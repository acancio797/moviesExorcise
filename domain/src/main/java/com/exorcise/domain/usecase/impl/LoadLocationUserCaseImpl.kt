package com.exorcise.domain.usecase.impl

import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.repository.LocationRepository
import com.exorcise.domain.usecase.LoadLocationUserCase
import javax.inject.Inject

class LoadLocationUserCaseImpl @Inject constructor(private val locationRepository: LocationRepository) :
    LoadLocationUserCase {

    override suspend operator fun invoke(): Result<List<MapPoint?>> {
        return locationRepository.loadLocations()
    }

}