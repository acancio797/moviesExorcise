package com.exorcise.movie.usercase

import com.exorcise.domain.model.PersonDetails
import com.exorcise.domain.repository.PersonRepository
import com.exorcise.domain.usecase.impl.GetPopularPersonUserCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPopularPersonUserCaseImplTest {

    @Test
    fun InvokeShouldReturnPersonDetailsWhenRepositoryCallSucceeds() = runTest {
        // Arrange
        val expectedPerson = PersonDetails(
            id = 1,
            name = "John Doe",
            adult = false,
            alsoKnownAs = listOf("JD"),
            biography = "Actor biography",
            birthday = "1980-01-01",
            deathday = null,
            gender = 1,
            homepage = "https://example.com",
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            placeOfBirth = "New York, USA",
            popularity = 10.5,
            profilePath = "/profile.jpg"
        )

        val mockRepository = mockk<PersonRepository> {
            coEvery { getPopularPerson() } returns Result.success(expectedPerson)
        }

        val useCase = GetPopularPersonUserCaseImpl(mockRepository)

        // Act
        val result = useCase.invoke()

        // Assert
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be success", result.isSuccess)
        assertEquals(expectedPerson, result.getOrNull())
    }

    @Test
    fun invokeShouldReturnFailureWhenRepositoryCallFails() = runTest {
        // Arrange
        val errorMessage = "Failed to fetch person details"
        val mockRepository = mockk<PersonRepository> {
            coEvery { getPopularPerson() } returns Result.failure(Throwable(errorMessage))
        }

        val useCase = GetPopularPersonUserCaseImpl(mockRepository)

        // Act
        val result = useCase.invoke()

        // Assert
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be failure", result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
    }
}
