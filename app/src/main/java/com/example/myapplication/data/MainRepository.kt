package com.example.myapplication.data

import android.content.ContentValues
import android.database.Cursor
import com.example.myapplication.R
import com.example.myapplication.data.dao.FilmDao
import com.example.myapplication.data.entity.Film
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {

    fun putToDb(films: List<Film>) {
        //Запросы в бд должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): List<Film> {
        return filmDao.getCachedFilms()
    }
}