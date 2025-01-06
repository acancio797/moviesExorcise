package com.exorcise.movie.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exorcise.movie.data.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(private val moviesRepo: MoviesRepository) :
    ViewModel() {

    private val _username = MutableLiveData("John Doe")
    val username: LiveData<String> = _username

    fun setUsername(newName: String) {
        _username.value = newName
    }
}