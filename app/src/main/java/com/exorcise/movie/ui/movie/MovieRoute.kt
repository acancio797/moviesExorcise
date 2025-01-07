package com.exorcise.movie.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel

class MovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MovieRoute(viewModel = hiltViewModel(), onSelectMovie = {})
            }
        }
    }

    @Composable
    fun MovieRoute(
        viewModel: MovieViewModel,
        onSelectMovie: (Int) -> Unit,
    ) {
        val lazyListState: LazyListState = rememberLazyListState()

        viewModel.uiState.collectAsState().value.let { state ->
            when (state) {
                is MovieUiState.HasMovies -> MovieFeedScreen(
                    uiState = state,
                    moviesLazyListState = lazyListState,
                    onGetMovies = {
                        viewModel.refreshMovies()
                    },
                    onSelectMovie = onSelectMovie,
                    onGetPopularTv = {
                        viewModel.getPopularTv()
                    },
                    onSelectType = 0
                )

                is MovieUiState.NoMovies -> NoMoviesScreen(
                    uiState = state,
                    onGetMovies = {
                        viewModel.refreshMovies()
                    }
                )

                else -> {}
            }
        }
    }
}
