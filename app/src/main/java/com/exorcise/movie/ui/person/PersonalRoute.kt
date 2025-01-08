package com.exorcise.movie.ui.person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.exorcise.movie.ui.base.LoadingMoviesScreen


@Composable
fun PersonalRoute(
    viewModel: PersonalViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetails()
    }

    viewModel.uiState.collectAsState().value.let {
        when (it) {
            is PersonalUiState.HasDetails -> {
                PersonalFragmentHome(personDetails = it.movieDetails)
            }

            is PersonalUiState.Empty -> {
                LoadingMoviesScreen()
            }
            else -> {}
        }
    }
}