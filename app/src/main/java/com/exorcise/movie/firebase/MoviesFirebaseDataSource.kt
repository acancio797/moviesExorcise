package com.exorcise.movie.firebase

import com.exorcise.movie.di.IODispatcher
import com.exorcise.movie.model.MapPoint
import com.exorcise.movie.model.MovieGeolocation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesFirebaseDataSource @Inject constructor(
    private val client: MoviesFirebaseClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getMoviesGeolocations() = withContext(ioDispatcher) {
        client.getMoviesGeo()
    }

    suspend fun insertMoviesGeolocations(movie: MapPoint) = withContext(ioDispatcher) {
        client.insertMovie(movie)
    }
}