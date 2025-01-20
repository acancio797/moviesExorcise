package com.exorcise.movie.data.location

import com.exorcise.data.data.firebase.MoviesFirebaseDataSource
import com.exorcise.data.data.location.LocationRepositoryImpl
import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.repository.LocationRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocationRepositoryImplTest {

    private lateinit var moviesFirebaseDataSource: MoviesFirebaseDataSource
    private lateinit var locationRepository: LocationRepository

    @Before
    fun setup() {

        moviesFirebaseDataSource = mockk(relaxed = true)
        locationRepository = LocationRepositoryImpl(moviesFirebaseDataSource)
    }

    @Test
    fun `saveLocation should call insertMoviesGeolocations on data source`() = runTest {
        val mapPoint = MapPoint(position = mockk(), time = mockk(), id = "123")

        locationRepository.saveLocation(mapPoint)

        coVerify { moviesFirebaseDataSource.insertMoviesGeolocations(mapPoint) }
    }

    @Test
    fun `loadLocations should return success with list of MapPoints`() = runTest {
        val mockLocations = listOf(
            MapPoint(position = mockk(), time = mockk(), id = "1"),
            MapPoint(position = mockk(), time = mockk(), id = "2")
        )

        coEvery { moviesFirebaseDataSource.getMoviesGeolocations() } returns mockLocations

        val result = locationRepository.loadLocations()

        assertTrue(result.isSuccess)
        assertEquals(mockLocations, result.getOrNull())

        coVerify { moviesFirebaseDataSource.getMoviesGeolocations() }
    }

    @Test
    fun `loadLocations should return failure when an exception occurs`() = runTest {
        val mockException = Exception("Failed to load locations")

        coEvery { moviesFirebaseDataSource.getMoviesGeolocations() } throws mockException

        val result = locationRepository.loadLocations()

        assertTrue(result.isFailure)
        assertEquals(mockException, result.exceptionOrNull())

        coVerify { moviesFirebaseDataSource.getMoviesGeolocations() }
    }
}
