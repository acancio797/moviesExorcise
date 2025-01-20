package com.exorcise.movie.data.person

import com.exorcise.data.api.MoviesApiClient
import com.exorcise.data.api.responses.PersonDetailResponse
import com.exorcise.data.api.responses.PopularPersonResponse
import com.exorcise.data.data.person.PersonRemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersonRemoteDataSourceTest {

    private lateinit var moviesApiClient: MoviesApiClient
    private lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var personRemoteDataSource: PersonRemoteDataSource

    @Before
    fun setup() {
        moviesApiClient = mockk(relaxed = true)
        ioDispatcher = UnconfinedTestDispatcher(TestScope().testScheduler) // Dispatcher para pruebas
        personRemoteDataSource = PersonRemoteDataSource(moviesApiClient, ioDispatcher)
    }

    @Test
    fun getPopularPersonShouldReturnPopularPersonResponseWhenSuccessful() = runTest {
        val mockResponse = PopularPersonResponse(
            page = 1,
            totalPages = 1,
            totalResults = 1,
            popularPerson = listOf(
                mockk(relaxed = true) // Simular PersonResponse
            )
        )

        coEvery { moviesApiClient.getPopularPerson(1) } returns Result.success(mockResponse)

        val result = personRemoteDataSource.getPopularPerson(1)

        assertTrue(result.isSuccess)
        assertEquals(mockResponse, result.getOrNull())
    }

    @Test
    fun getPopularPersonShouldReturnFailureWhenAPICallFails() = runTest {
        val exception = Exception("API call failed")

        coEvery { moviesApiClient.getPopularPerson(1) } returns Result.failure(exception)

        val result = personRemoteDataSource.getPopularPerson(1)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun getMostPopularShouldReturnPersonDetailResponseWhenSuccessful() = runTest {
        val mockResponse = PersonDetailResponse(
            adult = false,
            alsoKnownAs = listOf("Alias 1", "Alias 2"),
            biography = "Biography of the person",
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

        coEvery { moviesApiClient.getPersonDetails(1) } returns Result.success(mockResponse)

        val result = personRemoteDataSource.getMostPopular(1)

        assertTrue(result.isSuccess)
        assertEquals(mockResponse, result.getOrNull())
    }

    @Test
    fun getMostPopularShouldReturnFailureWhenAPICallFails() = runTest {
        val exception = Exception("API call failed")

        coEvery { moviesApiClient.getPersonDetails(1) } returns Result.failure(exception)

        val result = personRemoteDataSource.getMostPopular(1)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
