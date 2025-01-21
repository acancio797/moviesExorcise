package com.exorcise.movie.ui.map

import com.exorcise.domain.model.MapPoint
import com.exorcise.domain.usecase.LoadLocationUserCase
import com.exorcise.movie.ui.BaseViewModelTest
import com.google.android.gms.maps.model.LatLng
import com.google.type.DateTime
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest : BaseViewModelTest() {

    @Test
    fun fetchMarkersSuccess() = runTest {
        val expectedMarkers: List<MapPoint?> = listOf(
            MapPoint(LatLng(1.0, 2.0), DateTime.getDefaultInstance(), "marker1"),
            MapPoint(LatLng(1.0, 2.0), DateTime.getDefaultInstance(), "marker2"),
        )
        val mockLoadLocationUserCase = mockk<LoadLocationUserCase>()

        coEvery { mockLoadLocationUserCase.invoke() } returns Result.success<List<MapPoint?>>(
            expectedMarkers
        )


        val mapViewModel = MapViewModel(mockLoadLocationUserCase)

        val state = mapViewModel.uiState.value as? MapUiState.HasMarkers
        assertNotNull("Current state not expected", state)
        assertEquals(expectedMarkers, state?.markers)
        assertEquals(false, state?.isLoading)
    }

    @Test
    fun fetchMarkersError() = runTest {
        val errorMessage = "Failed to load markers"

        val mockLoadLocationUserCase = mockk<LoadLocationUserCase>()

        coEvery { mockLoadLocationUserCase.invoke() } returns Result.failure(Exception(errorMessage))


        val mapViewModel = MapViewModel(mockLoadLocationUserCase)

        val state = mapViewModel.uiState.value as? MapUiState.NoMarkers
        assertNotNull("Current state not expected", state)
        assertEquals(false, state?.isLoading)
    }

    @Test
    fun fetchMarkersEmptyList() = runTest {
        val expectedMarkers = emptyList<MapPoint>()

        val mockLoadLocationUserCase = mockk<LoadLocationUserCase>()

        coEvery { mockLoadLocationUserCase.invoke() } returns Result.success(expectedMarkers)


        val mapViewModel = MapViewModel(mockLoadLocationUserCase)

        val state = mapViewModel.uiState.value as? MapUiState.NoMarkers
        assertNotNull("Current state not expected", state)
        assertEquals(false, state?.isLoading)
    }
}

