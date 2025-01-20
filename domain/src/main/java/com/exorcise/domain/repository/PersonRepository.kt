package com.exorcise.domain.repository

import com.exorcise.domain.model.PersonDetails


interface PersonRepository {
    suspend fun getPopularPerson() :Result<PersonDetails?>
}