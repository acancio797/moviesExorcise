package com.exorcise.movie.ui.photo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.usecase.UploadImageUseCase
import com.exorcise.movie.ui.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class PhotosViewModelTest : BaseViewModelTest() {

    @Test
    fun uploadImageSuccess() = runTest {
        val expectedUrl = "http://example.com/image.jpg"
        val mockImageFile = ImageFile(uri = mockk(), fileName = "test.jpg")
        val mockUseCase = mockk<UploadImageUseCase>()

        coEvery { mockUseCase.invoke(mockImageFile) } returns expectedUrl

        val viewModel = PhotosViewModel(mockUseCase)

        val stateObserver = mutableListOf<Result<String?>>()
        viewModel.imageUploadState.observeForever {
            stateObserver.add(it)
        }

        viewModel.uploadImage(mockImageFile)

        advanceUntilIdle()
        shadowOf(android.os.Looper.getMainLooper()).idle()

        assertTrue("State observer should capture at least one value", stateObserver.isNotEmpty())
        val state = stateObserver.last()
        assertNotNull("Image upload state should not be null", state)
        assertTrue("Image upload result should be success", state.isSuccess)
        assertEquals(expectedUrl, state.getOrNull())
    }

    @Test
    fun uploadImageFailure() = runTest {
        val errorMessage = "Upload failed"
        val mockImageFile = ImageFile(uri = mockk(), fileName = "test.jpg")
        val mockUseCase = mockk<UploadImageUseCase>()

        coEvery { mockUseCase.invoke(mockImageFile) } throws Exception(errorMessage)

        val viewModel = PhotosViewModel(mockUseCase)

        val stateObserver = mutableListOf<Result<String?>>()
        viewModel.imageUploadState.observeForever {
            stateObserver.add(it)
        }

        viewModel.uploadImage(mockImageFile)

        advanceUntilIdle()
        shadowOf(android.os.Looper.getMainLooper()).idle()

        assertTrue("State observer should capture at least one value", stateObserver.isNotEmpty())
        val state = stateObserver.last()
        assertNotNull("Image upload state should not be null", state)
        assertTrue("Image upload result should be failure", state.isFailure)
        assertEquals(errorMessage, state.exceptionOrNull()?.message)
    }

    @Test
    fun checkAndRequestPermissionGranted() {
        val context = mockk<Context> {
            every {
                ContextCompat.checkSelfPermission(
                    this@mockk,
                    any()
                )
            } returns PackageManager.PERMISSION_GRANTED
        }

        val mockIntent = slot<Intent>()
        val mockImagePickerLauncher = mockk<(Intent) -> Unit>(relaxed = true)
        val mockPermissionLauncher = mockk<(String) -> Unit>(relaxed = true)

        val viewModel = PhotosViewModel(mockk())

        viewModel.checkAndRequestPermission(
            context,
            mockPermissionLauncher,
            mockImagePickerLauncher
        )

        shadowOf(android.os.Looper.getMainLooper()).idle()

        verify(exactly = 1) {
            mockImagePickerLauncher.invoke(capture(mockIntent))
        }

        assertEquals(Intent.ACTION_PICK, mockIntent.captured.action)
        assertEquals("image/*", mockIntent.captured.type)
        verify(exactly = 0) { mockPermissionLauncher.invoke(any()) }
    }


    @Test
    fun checkAndRequestPermissionDenied() {
        val permission = "android.permission.READ_EXTERNAL_STORAGE"

        val activity = mockk<Activity>(relaxed = true)

        mockkStatic(ContextCompat::class)
        mockkStatic(ActivityCompat::class)

        every {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            )
        } returns PackageManager.PERMISSION_DENIED
        every {
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            )
        } returns false

        val mockImagePickerLauncher = mockk<(Intent) -> Unit>(relaxed = true)
        val mockPermissionLauncher = mockk<(String) -> Unit>(relaxed = true)

        val viewModel = PhotosViewModel(mockk())

        viewModel.checkAndRequestPermission(
            activity,
            mockPermissionLauncher,
            mockImagePickerLauncher
        )

        shadowOf(android.os.Looper.getMainLooper()).idle()

        verify(exactly = 0) { mockImagePickerLauncher.invoke(any()) }
        verify(exactly = 1) { mockPermissionLauncher.invoke(permission) }

        unmockkStatic(ContextCompat::class)
        unmockkStatic(ActivityCompat::class)
    }


}
