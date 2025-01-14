package com.exorcise.movie.ui.map

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import com.exorcise.movie.model.MapPoint
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class MapFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MapRoute(viewModel = hiltViewModel())
            }
        }
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MapFragmentHome(
    markers: List<MapPoint?> = emptyList(),
) {

    MaterialTheme {
        Scaffold(
            modifier = Modifier,
        ) { _ ->
            GoogleMapScreenWithMarkers(markers)
        }

    }
}

@Composable
fun GoogleMapScreenWithMarkers(markers: List<MapPoint?> = emptyList()) {

    var sydney = LatLng(-33.852, 151.211)
    if (markers.isNotEmpty()) {
        sydney = markers.first()?.position ?: LatLng(0.0, 0.0)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(sydney, 10f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        markers.forEach { marker ->
            Marker(
                state = MarkerState(position = marker?.position ?: LatLng(0.0, 0.0)),
                title = marker?.time.toString(),
            )
        }
    }
}
