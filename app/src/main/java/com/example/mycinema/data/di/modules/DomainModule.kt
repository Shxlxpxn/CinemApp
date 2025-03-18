package com.example.mycinema.data.di.modules

import com.example.mycinema.data.MainRepository
import com.example.mycinema.data.TmdbApi
import com.example.mycinema.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}