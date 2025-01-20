package com.exorcise.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exorcise.domain.model.MovieSummary
import com.exorcise.domain.utils.Converters

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