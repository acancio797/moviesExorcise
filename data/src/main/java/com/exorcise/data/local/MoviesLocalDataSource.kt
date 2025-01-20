package com.exorcise.data.local

import com.exorcise.domain.di.IODispatcher
import com.exorcise.domain.model.MovieSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesLocalDataSource@Inject constructor(
    private val movieDao: MovieDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getGetLocalMovies() = withContext(ioDispatcher) {
        movieDao.getAllMovies();
    }

    suspend fun incertMovies(movies: List<MovieSummary>) = withContext(ioDispatcher) {
        movieDao.insertarDatosLocales(movies)
    }
}