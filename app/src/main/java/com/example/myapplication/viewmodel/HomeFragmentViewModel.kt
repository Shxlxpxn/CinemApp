package com.example.myapplication.viewmodel


import androidx.lifecycle.ViewModel
import com.example.myapplication.App
import com.example.myapplication.data.entity.Film
import com.example.myapplication.domain.Interactor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val showProgressBar: Channel<Boolean>
    @Inject
    //Инициализируем интерактор
    lateinit var interactor: Interactor
    val filmsListData: Flow<List<Film>>

    init {
        App.instance.dagger.inject(this)
        showProgressBar = interactor.progressBarState
        filmsListData  = interactor.getFilmsFromDB()
        getFilms()
    }

    fun getFilms() {
        interactor.getFilmsFromApi(1)
    }
}