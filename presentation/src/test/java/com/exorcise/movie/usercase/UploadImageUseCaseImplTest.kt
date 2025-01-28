package com.exorcise.movie.usercase

import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.repository.ImageRepository
import com.exorcise.domain.usecase.impl.UploadImageUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UploadImageUseCaseImplTest {

    @Test
    fun invokeShouldReturnImageURLWhenRepositoryCallSucceeds() = runTest {
        // Arrange
        val expectedUrl = "http://example.com/image.jpg"
        val mockImageFile = ImageFile(uri = mockk(), fileName = "test.jpg")

        val mockRepository = mockk<ImageRepository> {
            coEvery { uploadImage(mockImageFile) } returns expectedUrl
        }

        val useCase = UploadImageUseCaseImpl(mockRepository)

        // Act
        val result = useCase.invoke(mockImageFile)

        // Assert
        assertNotNull("Result should not be null", result)
        assertEquals(expectedUrl, result)
    }

    @Test
    fun invokeShouldReturnImageURLWhenRepositoryCallFails() = runTest {
        // Arrange
        val mockImageFile = ImageFile(uri = mockk(), fileName = "test.jpg")

        val mockRepository = mockk<ImageRepository> {
            coEvery { uploadImage(mockImageFile) } returns null
        }

        val useCase = UploadImageUseCaseImpl(mockRepository)

        // Act
        val result = useCase.invoke(mockImageFile)

        // Assert
        assertNull("Result should not be null", result)
        assertEquals(null, result)
    }
}
