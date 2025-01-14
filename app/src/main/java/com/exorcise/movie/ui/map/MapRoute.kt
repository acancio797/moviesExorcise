package com.exorcise.movie.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.exorcise.movie.ui.base.LoadingMoviesScreen


@Composable
fun MapRoute(
    viewModel: MapViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchMarkers()
    }
    viewModel.uiState.collectAsState().value.let {
        when (it) {
            is MapUiState.HasMarkers -> {
                if (it.isLoading) {
                    LoadingMoviesScreen()
                } else {
                    MapFragmentHome(markers = it.markers)
                }
            }

            else -> {
                MapFragmentHome()
            }

        }
    }
}