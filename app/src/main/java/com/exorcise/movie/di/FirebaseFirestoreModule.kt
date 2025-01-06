package com.exorcise.movie.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseFirestoreModule {

    @Singleton
    @Provides
    fun provideFirestore(@ApplicationContext appContext: Context) =
        FirebaseApp.initializeApp(appContext)

    @Singleton
    @Provides
    fun provideFirestoreDb() = FirebaseFirestore.getInstance()
}