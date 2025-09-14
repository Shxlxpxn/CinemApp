package com.example.myapplication

import android.app.Application
import com.amsdevelops.filmssearch.di.AppComponent
import com.amsdevelops.filmssearch.di.DaggerAppComponent
import com.example.myapplication.data.di.modules.DatabaseModule
import com.example.myapplication.data.di.modules.DomainModule
import com.example.myapplication.data.di.modules.RemoteModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}