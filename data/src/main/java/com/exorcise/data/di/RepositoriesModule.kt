package com.exorcise.data.di

import com.exorcise.data.data.configuration.ConfigurationRemoteDataSource
import com.exorcise.data.data.configuration.ConfigurationRepositoryImpl
import com.exorcise.data.data.firebase.MoviesFirebaseDataSource
import com.exorcise.data.data.image.ImageRepositoryImpl
import com.exorcise.data.data.location.LocationRepositoryImpl
import com.exorcise.data.data.movies.MoviesRemoteDataSource
import com.exorcise.data.data.movies.MoviesRepositoryImpl
import com.exorcise.data.data.person.PersonRemoteDataSource
import com.exorcise.data.data.person.PersonRepositoryImpl
import com.exorcise.data.local.MoviesLocalDataSource
import com.exorcise.domain.repository.ConfigurationRepository
import com.exorcise.domain.repository.ImageRepository
import com.exorcise.domain.repository.LocationRepository
import com.exorcise.domain.repository.MoviesRepository
import com.exorcise.domain.repository.PersonRepository
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

        ): MoviesRepository =
        MoviesRepositoryImpl(
            moviesRemoteDataSource,
            configurationRepository,
            moviesLocalDataSource
        )

    @Provides
    fun provideLocationRepository(
        moviesFirebaseDataSource: MoviesFirebaseDataSource
    ): LocationRepository =
        LocationRepositoryImpl(moviesFirebaseDataSource)

    @Provides
    fun provideImageRepository(
        moviesFirebaseDataSource: MoviesFirebaseDataSource
    ): ImageRepository =
        ImageRepositoryImpl(moviesFirebaseDataSource)


    @Provides
    fun providePersonRepository(
        personRemoteDataSource: PersonRemoteDataSource,
        configurationRepository: ConfigurationRepository,
    ): PersonRepository =
        PersonRepositoryImpl(
            personRemoteDataSource,
            configurationRepository
        )


    @Provides
    fun provideConfigurationRepository(
        configurationRemoteDataSource: ConfigurationRemoteDataSource
    ): ConfigurationRepository =
        ConfigurationRepositoryImpl(
            configurationRemoteDataSource
        )
}
