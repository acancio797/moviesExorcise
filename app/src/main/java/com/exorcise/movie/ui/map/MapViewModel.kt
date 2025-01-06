package com.exorcise.movie.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exorcise.movie.data.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val _location = MutableLiveData("Buenos Aires")
    val location: LiveData<String> = _location

    fun updateLocation(newLocation: String) {
        _location.value = newLocation
    }
}