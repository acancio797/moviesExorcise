package com.exorcise.movie.data.person

import com.exorcise.movie.api.MoviesApiClient
import com.exorcise.movie.di.IODispatcher
import com.exorcise.movie.local.MovieDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(
    private val moviesApiClient: MoviesApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPopularPerson(page: Int) = withContext(ioDispatcher) {
        moviesApiClient.getPopularPerson(page)
    }

    suspend fun getMostPopular(id: Int) = withContext(ioDispatcher) {
        moviesApiClient.getPersonDetails(id)
    }
}