package com.amsdevelops.filmssearch.di


import com.example.myapplication.data.di.modules.DatabaseModule
import com.example.myapplication.data.di.modules.DomainModule
import com.example.myapplication.data.di.modules.RemoteModule
import com.example.myapplication.viewmodel.HomeFragmentViewModel
import com.example.myapplication.viewmodel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)
interface AppComponent {
    //метод для того, чтобы появилась возможность внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    //метод для того, чтобы появилась возможность внедрять зависимости в SettingsFragmentViewModel
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
}