package com.exorcise.domain.usecase.impl

import com.exorcise.domain.model.PersonDetails
import com.exorcise.domain.repository.LocationRepository
import com.exorcise.domain.repository.PersonRepository
import com.exorcise.domain.usecase.GetPopularPersonUserCase
import com.exorcise.domain.usecase.LoadLocationUserCase
import javax.inject.Inject

class GetPopularPersonUserCaseImpl @Inject constructor(private val personRepository: PersonRepository) :
    GetPopularPersonUserCase {

    override suspend fun invoke(): Result<PersonDetails?> {
        return personRepository.getPopularPerson()
    }

}