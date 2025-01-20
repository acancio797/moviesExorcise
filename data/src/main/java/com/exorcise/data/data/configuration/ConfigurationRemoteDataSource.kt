package com.exorcise.data.data.configuration

import com.exorcise.domain.di.IODispatcher
import com.exorcise.data.local.MovieDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfigurationRemoteDataSource @Inject constructor(
    private val moviesApiClient: com.exorcise.data.api.MoviesApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getApiConfiguration() = withContext(ioDispatcher) {
        moviesApiClient.getApiConfiguration()
    }
}
