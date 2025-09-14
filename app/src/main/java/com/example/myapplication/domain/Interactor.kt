package com.example.myapplication.domain


import com.example.myapplication.data.*
import com.example.myapplication.data.entity.Film
import com.example.myapplication.data.entity.Root
import com.example.myapplication.data.preferences.PreferenceProvider
import com.example.myapplication.utils.Converter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState = Channel<Boolean>(Channel.CONFLATED)

    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        scope.launch {
            progressBarState.send(true)
        }
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), ApiConstants.API_KEY, "ru-RU", page).enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                val list = Converter.convertApiListToDTOList(response.body()?.results)
                //Кладем фильмы в бд
                scope.launch {
                    repo.putToDb(list)
                    progressBarState.send(false)
                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                scope.launch {
                    progressBarState.send(false)
            }
        }
    })
}
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Flow<List<Film>> = repo.getAllFromDB()
}