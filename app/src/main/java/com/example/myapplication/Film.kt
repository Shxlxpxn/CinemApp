package com.example.myapplication

@Parcelize
data class Film(
    val title: String,
    val poster: Int,
    val description: String
) : Parcelable