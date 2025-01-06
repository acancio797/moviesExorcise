package com.exorcise.movie.data.movies

import com.exorcise.movie.api.MoviesApiClient
import com.exorcise.movie.di.IODispatcher
import com.exorcise.movie.local.MovieDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesApiClient: MoviesApiClient,
    private val movieDao: MovieDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPopularMovies(page: Int) = withContext(ioDispatcher) {
        moviesApiClient.getPopularMovies(page)
    }

    suspend fun getPopularTv(page: Int) = withContext(ioDispatcher) {
        moviesApiClient.getPopularSeries(page)
    }

    suspend fun getMovieDetails(id: Int) = withContext(ioDispatcher) {
        moviesApiClient.getMovieDetails(id)
    }


}
