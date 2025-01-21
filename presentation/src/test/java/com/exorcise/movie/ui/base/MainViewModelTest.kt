package com.exorcise.movie.ui.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.Observer
import com.exorcise.movie.ui.components.BottomNavItem
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var scheduler: TestCoroutineScheduler
    private lateinit var dispatcher: TestDispatcher

    @Before
    fun setup() {
        scheduler = TestCoroutineScheduler()
        dispatcher = StandardTestDispatcher(scheduler)
        viewModel = MainViewModel()
    }

    @Test
    fun selectTabShouldUpdateSelectedTabLiveData() = runTest(scheduler) {

        assertEquals(BottomNavItem.Personal, viewModel.selectedTab.value)


        viewModel.selectTab(BottomNavItem.Personal)


        assertEquals(BottomNavItem.Personal, viewModel.selectedTab.value)
    }

    @Test
    fun selectedTabLiveDataShouldNotifyObserversWhenUpdated() = runTest(scheduler) {
        val observer = mockk<Observer<BottomNavItem>>(relaxed = true)
        viewModel.selectedTab.observeForever(observer)

        viewModel.selectTab(BottomNavItem.Photos)

        verify { observer.onChanged(BottomNavItem.Photos) }

        viewModel.selectedTab.removeObserver(observer)
    }
}

