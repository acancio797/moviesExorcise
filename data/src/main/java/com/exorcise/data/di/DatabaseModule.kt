package com.exorcise.data.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.exorcise.data.local.MovieDao
import com.exorcise.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "movie.db"

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = databaseBuilder(
        appContext,
        MovieDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(
        db: MovieDatabase
    ): MovieDao {
        return db.getMovieDao()
    }


}