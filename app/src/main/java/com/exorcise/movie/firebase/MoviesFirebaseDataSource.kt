package com.exorcise.movie.firebase

import com.exorcise.movie.di.IODispatcher
import com.exorcise.movie.model.ImageFile
import com.exorcise.movie.model.MapPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesFirebaseDataSource @Inject constructor(
    private val client: GeoPointFirebaseClient,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getMoviesGeolocations() = withContext(ioDispatcher) {
        client.getMoviesGeo()
    }

    suspend fun insertMoviesGeolocations(movie: MapPoint) = withContext(ioDispatcher) {
        client.insertMovie(movie)
    }

    suspend fun updateMoviesGeolocations(imageFile: ImageFile) = withContext(ioDispatcher) {
        client.uploadImageToFirebase(imageFile)
    }

}