package com.example.mycinema

import android.app.Application
import com.amsdevelops.filmssearch.di.AppComponent
import com.amsdevelops.filmssearch.di.DaggerAppComponent
import com.example.mycinema.data.ApiConstants
import com.example.mycinema.data.MainRepository
import com.example.mycinema.data.TmdbApi
import com.example.mycinema.domain.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}