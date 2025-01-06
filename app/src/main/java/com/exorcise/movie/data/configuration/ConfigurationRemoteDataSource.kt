package com.exorcise.movie.data.configuration

import com.exorcise.movie.api.MoviesApiClient
import com.exorcise.movie.di.IODispatcher
import com.exorcise.movie.local.MovieDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationRemoteDataSource @Inject constructor(
    private val moviesApiClient: MoviesApiClient,
    private val movieDao: MovieDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getApiConfiguration() = withContext(ioDispatcher) {
        moviesApiClient.getApiConfiguration()
    }
}
