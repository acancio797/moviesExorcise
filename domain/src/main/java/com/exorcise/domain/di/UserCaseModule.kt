package com.exorcise.domain.di

import com.exorcise.domain.repository.ImageRepository
import com.exorcise.domain.repository.LocationRepository
import com.exorcise.domain.repository.PersonRepository
import com.exorcise.domain.usecase.GetPopularPersonUserCase
import com.exorcise.domain.usecase.LoadLocationUserCase
import com.exorcise.domain.usecase.UploadImageUseCase
import com.exorcise.domain.usecase.impl.GetPopularPersonUserCaseImpl
import com.exorcise.domain.usecase.impl.LoadLocationUserCaseImpl
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
    fun provideUploadImageUseCase(repository: ImageRepository): UploadImageUseCase =
        UploadImageUseCaseImpl(repository)

    @Provides
    fun provideLoadLocationUseCase(repository: LocationRepository): LoadLocationUserCase =
        LoadLocationUserCaseImpl(repository)

    @Provides
    fun providePopularPersonUseCase(repository: PersonRepository): GetPopularPersonUserCase =
        GetPopularPersonUserCaseImpl(repository)

}