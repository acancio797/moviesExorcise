package com.exorcise.movie.ui.photo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.exorcise.domain.model.ImageFile
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import java.util.UUID


class PhotoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                UploadImageScreen(
                    viewModel = hiltViewModel(),
                )
            }
        }
    }

}

@Composable
fun UploadImageScreen(viewModel: PhotosViewModel) {
    val context = LocalContext.current
    val imageUploadState by viewModel.imageUploadState.observeAsState()

    // Registrar el lanzador de permisos
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.onPermissionGranted()
            } else {
                Toast.makeText(
                    context,
                    "Es necesario otorgar permisos para acceder a la galería.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val uri = result.data?.data
                if (uri != null) {
                    viewModel.uploadImage(ImageFile(uri, UUID.randomUUID().toString()))
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            viewModel.checkAndRequestPermission(
                context = context,
                permissionLauncher = { permission ->
                    requestPermissionLauncher.launch(permission)
                },
                imagePickerLauncher = { imagePickerLauncher.launch(it) }
            )
        }) {
            Text(text = "Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (imageUploadState?.isSuccess == true) {

            Text(text = "Imagen subida con éxito: ${imageUploadState?.getOrNull()}")
        } else if (imageUploadState?.isFailure == true) {
            Text(text = "Error al subir la imagen.")
        } else {
            Text(text = "Selecciona una imagen para subir.")
        }

    }
}


