package com.exorcise.domain.usecase

import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.model.MapPoint

interface LoadLocationUserCase {
    suspend  operator fun invoke(): Result<List<MapPoint?>>
}