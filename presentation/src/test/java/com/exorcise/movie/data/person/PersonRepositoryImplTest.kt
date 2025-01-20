package com.exorcise.movie.data.person

import com.exorcise.data.api.responses.PersonDetailResponse
import com.exorcise.data.api.responses.PersonResponse
import com.exorcise.data.api.responses.PopularPersonResponse
import com.exorcise.data.data.person.PersonRepositoryImpl
import com.exorcise.domain.model.ApiConfiguration
import com.exorcise.domain.model.PersonDetails
import com.exorcise.domain.repository.ConfigurationRepository
import com.exorcise.domain.repository.PersonRepository
import com.exorcise.data.data.person.PersonRemoteDataSource
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersonRepositoryImplTest {

    private lateinit var moviesRemoteDataSource: PersonRemoteDataSource
    private lateinit var configurationRepository: ConfigurationRepository
    private lateinit var personRepository: PersonRepository

    @Before
    fun setup() {
        moviesRemoteDataSource = mockk(relaxed = true)
        configurationRepository = mockk(relaxed = true)
        personRepository = PersonRepositoryImpl(moviesRemoteDataSource, configurationRepository)
    }

    @Test
    fun getPopularPersonShouldReturnAPersonDetailsWhenSuccessful() = runTest {
        val apiConfiguration = ApiConfiguration(
            imagesBaseUrl = "https://image.tmdb.org/t/p/",
            posterSize = "w780/",
            backdropSize = "w780/"
        )

        val popularPersonResponse = PopularPersonResponse(
            page = 1,
            totalPages = 1,
            totalResults = 1,
            popularPerson = listOf(
                PersonResponse(
                    adult = false,
                    gender = 1,
                    id = 1,
                    knownFor = emptyList(),
                    knownForDepartment = "Acting",
                    name = "John Doe",
                    popularity = 95.5,
                    profilePath = "/path/to/profile"
                )
            )
        )

        val personDetailResponse = PersonDetailResponse(
            adult = false,
            alsoKnownAs = emptyList(),
            biography = "An actor from USA.",
            birthday = "1990-01-01",
            deathday = null,
            gender = 1,
            homepage = "http://example.com",
            id = 1,
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            name = "John Doe",
            placeOfBirth = "USA",
            popularity = 95.5,
            profilePath = "/path/to/profile"
        )

        val personDetails = PersonDetails(
            adult = false,
            alsoKnownAs = emptyList(),
            biography = "An actor from USA.",
            birthday = "1990-01-01",
            deathday = null,
            gender = 1,
            homepage = "http://example.com",
            id = 1,
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            name = "John Doe",
            placeOfBirth = "USA",
            popularity = 95.5,
            profilePath = "https://image.tmdb.org/t/p/w780//path/to/profile"
        )

        coEvery { configurationRepository.fetchConfiguration() } returns Result.success(
            apiConfiguration
        )
        coEvery { moviesRemoteDataSource.getPopularPerson(1) } returns Result.success(
            popularPersonResponse
        )
        coEvery { moviesRemoteDataSource.getMostPopular(1) } returns Result.success(
            personDetailResponse
        )

        val result = personRepository.getPopularPerson()

        assertTrue(result.isSuccess)
        assertEquals(personDetails, result.getOrNull())
    }

    @Test
    fun getDetailPersonShouldReturnPersonDetailsWhenSuccessful() = runTest {
        val apiConfiguration = ApiConfiguration(
            imagesBaseUrl = "https://image.tmdb.org/t/p/",
            posterSize = "w780/",
            backdropSize = "w780/"
        )

        val personDetailResponse = PersonDetailResponse(
            adult = false,
            alsoKnownAs = emptyList(),
            biography = "An actor from USA.",
            birthday = "1990-01-01",
            deathday = null,
            gender = 1,
            homepage = "http://example.com",
            id = 1,
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            name = "John Doe",
            placeOfBirth = "USA",
            popularity = 95.5,
            profilePath = "/path/to/profile"
        )

        val personDetails = PersonDetails(
            adult = false,
            alsoKnownAs = emptyList(),
            biography = "An actor from USA.",
            birthday = "1990-01-01",
            deathday = null,
            gender = 1,
            homepage = "http://example.com",
            id = 1,
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            name = "John Doe",
            placeOfBirth = "USA",
            popularity = 95.5,
            profilePath = "https://image.tmdb.org/t/p/w780//path/to/profile"
        )

        coEvery { configurationRepository.fetchConfiguration() } returns Result.success(
            apiConfiguration
        )
        coEvery { moviesRemoteDataSource.getMostPopular(1) } returns Result.success(
            personDetailResponse
        )

        val result = (personRepository as PersonRepositoryImpl).getDetailPerson(1)

        assertTrue(result.isSuccess)
        assertEquals(personDetails, result.getOrNull())
    }

    @Test
    fun `getDetailPerson should return failure when remote fetch fails`() = runTest {
        val apiConfiguration = ApiConfiguration(
            imagesBaseUrl = "https://image.tmdb.org/t/p/",
            posterSize = "w780/",
            backdropSize = "w780/"
        )
        val exception = Exception("Remote fetch failed")

        coEvery { configurationRepository.fetchConfiguration() } returns Result.success(
            apiConfiguration
        )
        coEvery { moviesRemoteDataSource.getMostPopular(1) } returns Result.failure(exception)

        val result = (personRepository as PersonRepositoryImpl).getDetailPerson(1)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}


