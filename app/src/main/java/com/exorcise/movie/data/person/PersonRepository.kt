package com.exorcise.movie.data.person

import com.exorcise.movie.model.PersonDetails

interface PersonRepository {
    suspend fun getPopularPerson() :Result<PersonDetails?>
}