package com.exorcise.movie.di

import com.exorcise.movie.data.image.ImageRepository
import com.exorcise.movie.usecase.UploadImageUseCase
import dagger.Component.Factory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserCaseModule {

    @Provides
    @Singleton
    fun provideUploadImageUseCase(repository: ImageRepository): UploadImageUseCase {
        return UploadImageUseCase(repository)
    }
}