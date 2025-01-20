package com.exorcise.domain.repository

import com.exorcise.domain.model.MapPoint


interface LocationRepository {
    suspend fun saveLocation(point: MapPoint)
    suspend fun loadLocations():Result<List<MapPoint?>>
}