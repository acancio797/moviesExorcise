package com.exorcise.movie.ui.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exorcise.movie.data.movies.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val moviesRepo: MoviesRepository) : ViewModel() {

    private val _photoCount = MutableLiveData(42)
    val photoCount: LiveData<Int> = _photoCount

    fun addPhoto() {
        _photoCount.value = _photoCount.value?.plus(1)
    }
}