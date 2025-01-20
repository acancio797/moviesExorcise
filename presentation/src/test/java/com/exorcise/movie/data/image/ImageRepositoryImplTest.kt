package com.exorcise.movie.data.image

import com.exorcise.data.data.firebase.MoviesFirebaseDataSource
import com.exorcise.data.data.image.ImageRepositoryImpl
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.repository.ImageRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImageRepositoryImplTest {

    private lateinit var moviesFirebaseDataSource: MoviesFirebaseDataSource
    private lateinit var imageRepository: ImageRepository

    @Before
    fun setup() {
        moviesFirebaseDataSource = mockk(relaxed = true)
        imageRepository = ImageRepositoryImpl(moviesFirebaseDataSource)
    }

    @Test
    fun `uploadImage should call updateMoviesGeolocations and return the result`() = runTest {
        val imageFile = ImageFile(fileName = "test.jpg", uri = mockk())
        val mockResult = "/images/test.jpg"

        coEvery { moviesFirebaseDataSource.updateMoviesGeolocations(imageFile) } returns mockResult

        val result = imageRepository.uploadImage(imageFile)

        assertEquals(mockResult, result)

        coVerify { moviesFirebaseDataSource.updateMoviesGeolocations(imageFile) }
    }
}
