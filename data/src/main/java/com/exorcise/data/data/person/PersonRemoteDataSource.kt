package com.exorcise.data.data.person

import com.exorcise.domain.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PersonRemoteDataSource @Inject constructor(
    private val moviesApiClient: com.exorcise.data.api.MoviesApiClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPopularPerson(page: Int) = withContext(ioDispatcher) {
        moviesApiClient.getPopularPerson(page)
    }

    suspend fun getMostPopular(id: Int) = withContext(ioDispatcher) {
        moviesApiClient.getPersonDetails(id)
    }
}