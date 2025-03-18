package com.amsdevelops.filmssearch.di


import com.example.mycinema.data.di.modules.DatabaseModule
import com.example.mycinema.data.di.modules.DomainModule
import com.example.mycinema.data.di.modules.RemoteModule
import com.example.mycinema.viewmodel.HomeFragmentViewModel
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
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}