package com.exorcise.movie.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exorcise.movie.data.movies.MoviesRepository
import com.exorcise.movie.model.MovieSummary
import com.exorcise.movie.model.TypeMovieOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(MovieViewModelState(isRefreshing = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshMovies(TypeMovieOrder.Popular)
    }

    fun refreshMovies(getType: TypeMovieOrder) {
        viewModelState.update { it.copy(isRefreshing = true, errorMessages = emptyList()) }

        viewModelScope.launch {
            val result = moviesRepo.fetchPopularMovies(1, getType)
            val localResult = moviesRepo.fetchLocal()
            viewModelState.update { state ->
                if (result.isSuccess) {
                    val feed = result.getOrElse { throwable ->
                        val errorMessages = state.errorMessages + (throwable.message ?: "")
                        return@update state.copy(
                            errorMessages = errorMessages,
                            isRefreshing = false
                        )
                    }
                    state.copy(moviesFeed = feed, isRefreshing = false, errorMessages = emptyList())
                } else {
                    val feed = localResult.getOrElse { throwable ->
                        val errorMessages = state.errorMessages + (throwable.message ?: "")
                        return@update state.copy(
                            errorMessages = errorMessages,
                            isRefreshing = false
                        )
                    }
                    state.copy(moviesFeed = feed, isRefreshing = false, errorMessages = emptyList())
                }
            }
        }
    }

    fun getPopularTv() {
        viewModelState.update { it.copy(isRefreshing = true, errorMessages = emptyList()) }
        viewModelScope.launch {
            val result = moviesRepo.fetchPopularTv(1)
            viewModelState.update { state ->
                val feed = result.getOrElse { throwable ->
                    val errorMessages = state.errorMessages + (throwable.message ?: "")
                    return@update state.copy(errorMessages = errorMessages, isRefreshing = false)
                }

                state.copy(moviesFeed = feed, isRefreshing = false, errorMessages = emptyList())
            }
        }
    }
}

sealed interface MovieUiState {
    val isRefreshing: Boolean
    val selected: Int
    val errorMessages: List<String>
    val hasError: Boolean
        get() = errorMessages.isNotEmpty()

    data class NoMovies(
        override val isRefreshing: Boolean,
        override val selected: Int,
        override val errorMessages: List<String>
    ) : MovieUiState

    data class HasMovies(
        val moviesFeed: List<MovieSummary>,
        val isMovies: Boolean = false,
        override val isRefreshing: Boolean,
        override val selected: Int,
        override val errorMessages: List<String>
    ) : MovieUiState
}

private data class MovieViewModelState(
    val moviesFeed: List<MovieSummary>? = null,
    val isRefreshing: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): MovieUiState =
        when {
            moviesFeed != null && moviesFeed.isNotEmpty() -> {
                MovieUiState.HasMovies(
                    moviesFeed = moviesFeed,
                    isRefreshing = isRefreshing,
                    selected = 0,
                    errorMessages = errorMessages,
                )
            }

            else -> {
                MovieUiState.NoMovies(
                    isRefreshing = isRefreshing,
                    selected = 0,
                    errorMessages = errorMessages,
                )
            }
        }
}
