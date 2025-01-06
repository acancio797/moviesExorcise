package com.exorcise.movie.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exorcise.movie.ui.components.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedTab = MutableLiveData<BottomNavItem>(BottomNavItem.Personal)
    val selectedTab: LiveData<BottomNavItem>  get() = _selectedTab

    fun selectTab(item: BottomNavItem) {
        _selectedTab.value = item
    }

}
