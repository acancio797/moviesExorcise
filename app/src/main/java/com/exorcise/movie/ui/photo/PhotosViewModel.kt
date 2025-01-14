package com.exorcise.movie.ui.photo

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalLifecycleOwner

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exorcise.movie.model.ImageFile
import com.exorcise.movie.usecase.UploadImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val uploadImageUseCase: UploadImageUseCase) :
    ViewModel() {

    private val _imageUploadState = MutableLiveData<Result<String?>>()
    val imageUploadState: LiveData<Result<String?>> get() = _imageUploadState

    fun uploadImage(imageFile: ImageFile) {
        viewModelScope.launch {
            try {
                val url = uploadImageUseCase(imageFile)
                _imageUploadState.postValue(Result.success(url))
            } catch (e: Exception) {
                _imageUploadState.postValue(Result.failure(e))
            }
        }
    }

    fun checkAndRequestPermission(
        context: Context,
        permissionLauncher: (String) -> Unit,
        imagePickerLauncher: (Intent) -> Unit
    ) {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                imagePickerLauncher(intent)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission) -> {
                Toast.makeText(
                    context,
                    "Este permiso es necesario para seleccionar imágenes desde la galería.",
                    Toast.LENGTH_LONG
                ).show()
                permissionLauncher(permission)
            }

            else -> {
                permissionLauncher(permission)
            }
        }
    }

    fun onPermissionGranted() {

    }

}