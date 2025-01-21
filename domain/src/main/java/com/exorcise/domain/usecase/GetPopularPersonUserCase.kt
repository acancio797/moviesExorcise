package com.exorcise.domain.usecase

import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.model.PersonDetails

interface GetPopularPersonUserCase {
    suspend operator fun invoke(): Result<PersonDetails?>
}