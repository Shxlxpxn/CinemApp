package com.example.myapplication.domain


import io.reactivex.rxjava3.core.Observable
import com.example.myapplication.data.*
import com.example.myapplication.data.entity.Film
import com.example.myapplication.data.entity.Root
import com.example.myapplication.data.preferences.PreferenceProvider
import com.example.myapplication.utils.Converter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi, private val preferences: PreferenceProvider) {
    val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), ApiConstants.API_KEY, "ru-RU", page).enqueue(object : Callback<Root> {
            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                val list = Converter.convertApiListToDTOList(response.body()?.results)
                Completable.fromSingle<List<Film>> {
                    repo.putToDb(list)
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                progressBarState.onNext(false)
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                scope.launch {
                    progressBarState.onNext(false)
            }
        }
    })
}

    fun getSearchResultFromApi(search: String): Observable<List<Film>> = retrofitService.getFilmFromSearch(ApiConstants.API_KEY, "ru-RU", search, 1)
        .map {
            Converter.convertApiListToDTOList(it.results)
        }

    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()
}