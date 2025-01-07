package com.exorcise.movie.data.location

import com.exorcise.movie.model.MapPoint

interface LocationRepository {
    suspend fun saveLocation(point: MapPoint)
    suspend fun loadLocations():Result<List<MapPoint?>>
}