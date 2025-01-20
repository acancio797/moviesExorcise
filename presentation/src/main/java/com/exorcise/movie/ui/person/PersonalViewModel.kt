package com.exorcise.movie.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exorcise.domain.model.PersonDetails
import com.exorcise.domain.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val peronRepo: PersonRepository) :
    ViewModel() {

    private val viewModelState = MutableStateFlow(DetailsPersonViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )


    fun fetchMovieDetails() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = peronRepo.getPopularPerson()
            viewModelState.update { state ->
                val movieDetails = result.getOrElse { throwable ->
                    val errorMessage = throwable.message ?: ""
                    return@update state.copy(errorMessage = errorMessage, isLoading = false)
                }

                state.copy(personDetails = movieDetails, isLoading = false)
            }
        }
    }
}


sealed interface PersonalUiState {
    data class Empty(
        val isLoading: Boolean,
        val errorMessage: String?,
    ) : PersonalUiState

    data class HasDetails(
        val movieDetails: PersonDetails
    ) : PersonalUiState
}

data class DetailsPersonViewModelState(
    val personDetails: PersonDetails? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    fun toUiState(): PersonalUiState = when {
        personDetails != null -> {
            PersonalUiState.HasDetails(
                movieDetails = personDetails
            )
        }

        else -> {
            PersonalUiState.Empty(
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }
    }
}