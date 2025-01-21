package com.exorcise.movie.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.usecase.LoadLocationUserCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val locationUserCase: LoadLocationUserCase) :
    ViewModel() {
    private val viewModelState = MutableStateFlow(MapViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        fetchMarkers()
    }

    fun fetchMarkers() {
        viewModelState.update { it.copy(isLoading = true, errorMessages = emptyList()) }

        viewModelScope.launch {
            val result = locationUserCase()
            viewModelState.update { state ->
                if (result.isSuccess) {
                    val markers = result.getOrElse { throwable ->
                        val errorMessages = state.errorMessages + (throwable.message ?: "")
                        return@update state.copy(
                            errorMessages = errorMessages,
                            isLoading = false
                        )
                    }

                    state.copy(isLoading = false, pointFeed = markers, errorMessages = emptyList())
                } else {
                    state.copy(isLoading = false, errorMessages = emptyList())
                }
            }
        }


    }
}

sealed interface MapUiState {
    val isLoading: Boolean

    data class NoMarkers(override val isLoading: Boolean) : MapUiState
    data class HasMarkers(override val isLoading: Boolean, val markers: List<MapPoint?>) :
        MapUiState
}

private data class MapViewModelState(
    val pointFeed: List<MapPoint?>? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<String> = emptyList()
) {
    fun toUiState(): MapUiState = when {
        pointFeed != null && pointFeed.isNotEmpty() -> {
            MapUiState.HasMarkers(
                markers = pointFeed,
                isLoading = false,
            )
        }

        else -> {
            MapUiState.NoMarkers(
                isLoading = false
            )
        }
    }

}