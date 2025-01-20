package com.exorcise.domain.di

import com.exorcise.domain.repository.ImageRepository
import com.exorcise.domain.usecase.UploadImageUseCase
import com.exorcise.domain.usecase.impl.UploadImageUseCaseImpl
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
    fun provideUploadImageUseCase(repository: ImageRepository): UploadImageUseCase =
        UploadImageUseCaseImpl(repository)

}