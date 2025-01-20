package com.exorcise.domain.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore(@ApplicationContext appContext: Context) =
        FirebaseApp.initializeApp(appContext)

    @Singleton
    @Provides
    fun provideFirestoreDb() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

}