package com.exorcise.movie.data.firebase

import com.exorcise.data.data.firebase.GeoPointFirebaseClient
import com.exorcise.data.data.firebase.MoviesFirebaseDataSource
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.model.MapPoint
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MoviesFirebaseDataSourceTest {

    private lateinit var client: GeoPointFirebaseClient
    private lateinit var ioDispatcher: CoroutineDispatcher
    private lateinit var dataSource: MoviesFirebaseDataSource

    @Before
    fun setup() {
        client = mockk(relaxed = true)
        ioDispatcher = UnconfinedTestDispatcher(TestScope().testScheduler)
        dataSource = MoviesFirebaseDataSource(client, ioDispatcher)
    }

    @Test
    fun getMoviesGeolocationsShouldCallClientGetMoviesGeoAndReturnTheResult() = runTest {
        val mockResult = listOf(
            MapPoint(position = mockk(), time = mockk(), id = "1"),
            MapPoint(position = mockk(), time = mockk(), id = "2")
        )

        coEvery { client.getMoviesGeo() } returns mockResult

        val result = dataSource.getMoviesGeolocations()

        assertNotNull(result)
        assertEquals(mockResult, result)
        coVerify { client.getMoviesGeo() }
    }

    @Test
    fun insertMoviesGeolocationsShouldCallClientInsertMovie() = runTest {
        val movie = MapPoint(position = mockk(), time = mockk(), id = "123")

        dataSource.insertMoviesGeolocations(movie)

        coVerify { client.insertMovie(movie) }
    }

    @Test
    fun updateMoviesGeolocationsShouldCallClientUploadImageToFirebaseAndReturnTheResult() =
        runTest {
            val imageFile = ImageFile(fileName = "test.jpg", uri = mockk())
            val mockPath = "/images/test.jpg"


            coEvery { client.uploadImageToFirebase(imageFile) } returns mockPath

            val result = dataSource.updateMoviesGeolocations(imageFile)


            assertEquals(mockPath, result)
            coVerify { client.uploadImageToFirebase(imageFile) }
        }
}
