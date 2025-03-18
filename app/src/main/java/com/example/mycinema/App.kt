package com.example.mycinema

import android.app.Application
import com.example.mycinema.data.di.AppComponent
import com.amsdevelops.filmssearch.di.DaggerAppComponent
import com.example.mycinema.data.di.modules.DatabaseModule
import com.example.mycinema.data.di.modules.DomainModule
import com.example.mycinema.data.di.modules.RemoteModule
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
    }

    companion object {
        lateinit var instance: App
            private set
    }
}