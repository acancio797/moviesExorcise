package com.exorcise.movie.usercase

import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.repository.LocationRepository
import com.exorcise.domain.usecase.impl.LoadLocationUserCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadLocationUserCaseImplTest {

    @Test
    fun invokeShouldReturnLocationsWhenRepositoryCallSucceeds() = runTest {
        // Arrange
        val expectedLocations = listOf(
            MapPoint(position = null, time = null, id = "1"),
            MapPoint(position = null, time = null, id = "2")
        )

        val mockRepository = mockk<LocationRepository> {
            coEvery { loadLocations() } returns Result.success(expectedLocations)
        }

        val useCase = LoadLocationUserCaseImpl(mockRepository)

        // Act
        val result = useCase.invoke()

        // Assert
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be success", result.isSuccess)
        assertEquals(expectedLocations, result.getOrNull())
    }

    @Test
    fun invokeShouldReturnLocationsWhenRepositoryCallFails() = runTest {
        // Arrange
        val errorMessage = "Failed to load locations"
        val mockRepository = mockk<LocationRepository> {
            coEvery { loadLocations() } returns Result.failure(Throwable(errorMessage))
        }

        val useCase = LoadLocationUserCaseImpl(mockRepository)

        // Act
        val result = useCase.invoke()

        // Assert
        assertNotNull("Result should not be null", result)
        assertTrue("Result should be failure", result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
    }
}
