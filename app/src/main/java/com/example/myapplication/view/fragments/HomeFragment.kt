package com.example.myapplication.view.fragments

import com.example.myapplication.view.rv_adapters.TopSpacingItemDecoration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.view.rv_adapters.FilmListRecyclerAdapter
import com.example.myapplication.view.MainActivity
import com.example.myapplication.utils.AnimationHelper
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.data.entity.Film
import com.example.myapplication.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var scope: CoroutineScope
    private val viewModel by lazy {
    ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java) }
    private var filmsDataBase = listOf<Film>()
    //Используем backing field
    set(value) {
        //Если придет такое же значение то мы выходим из метода
        if (field == value) return
        //Если прило другое значение, то кладем его в переменную
        field = value
        //Обновляем RV адаптер
        filmsAdapter.addItems(field)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{


            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }
                //Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault()).contains(newText.lowercase(Locale.getDefault()))
                }
                scope = CoroutineScope(Dispatchers.IO).also { scope ->
                    scope.launch {
                        viewModel.filmsListData.collect {
                            withContext(Dispatchers.Main) {
                                filmsAdapter.addItems(it)
                                filmsDataBase = it
                            }
                        }
                    }
                }

                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }

        })


        //находим наш RV
        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as? MainActivity)?.launchDetailsFragment(film)
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireActivity())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)
        AnimationHelper.performFragmentCircularRevealAnimation(binding.root, requireActivity(), 1)
    }
    override fun onStop() {
        super.onStop()
        scope.cancel()
    }


}
