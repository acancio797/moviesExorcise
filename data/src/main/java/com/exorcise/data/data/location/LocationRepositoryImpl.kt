package com.exorcise.data.data.location

import com.exorcise.data.data.firebase.MoviesFirebaseDataSource
import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val moviesFirebaseDataSource: MoviesFirebaseDataSource) :
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