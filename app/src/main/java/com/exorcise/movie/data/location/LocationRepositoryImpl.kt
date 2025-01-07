package com.exorcise.movie.data.location

import android.icu.util.Calendar
import com.exorcise.movie.data.movies.MoviesRepository
import com.exorcise.movie.firebase.MoviesFirebaseDataSource
import com.exorcise.movie.model.MapPoint
import com.exorcise.movie.model.MovieGeolocation
import com.google.firebase.firestore.GeoPoint

class LocationRepositoryImpl(private val moviesFirebaseDataSource: MoviesFirebaseDataSource) :
    LocationRepository {
    override suspend fun saveLocation(point: MapPoint) {
        moviesFirebaseDataSource.insertMoviesGeolocations(point)
    }

    override suspend fun loadLocations(): Result<List<MapPoint?>> {
        try {
            val locations = moviesFirebaseDataSource.getMoviesGeolocations()
            return Result.success(locations)
        } catch (ex: Exception) {
            return Result.failure(ex)
        }

    }
}