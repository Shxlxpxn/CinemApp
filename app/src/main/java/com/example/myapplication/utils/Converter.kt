package com.example.myapplication.utils


import com.example.myapplication.data.entity.Result
import com.example.myapplication.data.entity.Film

object Converter {
    fun convertApiListToDTOList(list: List<Result>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.title,
                poster = it.posterPath,
                description = it.overview,
                rating = it.voteAverage,
                isInFavorites = false
            ))
        }
        return result
    }
}