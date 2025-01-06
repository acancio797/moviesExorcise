package com.exorcise.movie.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exorcise.movie.model.MovieSummary
import com.exorcise.movie.utils.Converters

@Database(
    entities = [
        MovieSummary::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

}