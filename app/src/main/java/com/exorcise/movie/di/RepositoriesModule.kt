package com.exorcise.movie.di

import com.exorcise.movie.data.configuration.ConfigurationRemoteDataSource
import com.exorcise.movie.data.configuration.ConfigurationRepository
import com.exorcise.movie.data.configuration.ConfigurationRepositoryImpl
import com.exorcise.movie.data.movies.MoviesRemoteDataSource
import com.exorcise.movie.data.movies.MoviesRepository
import com.exorcise.movie.data.movies.MoviesRepositoryImpl
import com.exorcise.movie.firebase.MoviesFirebaseDataSource
import com.exorcise.movie.local.MoviesLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideMoviesRepository(
        moviesRemoteDataSource: MoviesRemoteDataSource,
        configurationRepository: ConfigurationRepository,
        moviesLocalDataSource: MoviesLocalDataSource,
        moviesFirebaseDataSource: MoviesFirebaseDataSource
    ): MoviesRepository =
        MoviesRepositoryImpl(moviesRemoteDataSource, configurationRepository,moviesLocalDataSource,moviesFirebaseDataSource)

    @Provides
    fun provideConfigurationRepository(
        configurationRemoteDataSource: ConfigurationRemoteDataSource
    ): ConfigurationRepository =
        ConfigurationRepositoryImpl(configurationRemoteDataSource)
}
