package com.exorcise.movie.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exorcise.movie.model.MovieSummary

@Dao
interface MovieDao {



    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieSummary>

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id = :id)")
    fun getMovie(id: Int): LiveData<Boolean>

    @Delete
    suspend fun deleteMovie(media: MovieSummary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieSummary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarDatosLocales(datos: List<MovieSummary>)

}