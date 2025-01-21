package com.exorcise.movie.ui.person


import com.exorcise.domain.model.PersonDetails
import com.exorcise.domain.usecase.GetPopularPersonUserCase
import com.exorcise.movie.ui.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersonalViewModelTest : BaseViewModelTest() {

    @Test
    fun fetchMovieDetailsSuccess() = runTest {
        val expectedDetails = PersonDetails(
            id = 1,
            name = "John Doe",
            biography = "An actor from Hollywood.",
            birthday = "1990-01-01",
            deathday = null,
            gender = 1,
            homepage = "http://example.com",
            imdbId = "nm1234567",
            knownForDepartment = "Acting",
            placeOfBirth = "USA",
            popularity = 8.5,
            profilePath = "/profile.jpg",
            adult = false,
            alsoKnownAs = listOf("Johnny", "JD")
        )
        val mockUseCase = mockk<GetPopularPersonUserCase>()
        coEvery { mockUseCase.invoke() } returns Result.success(expectedDetails)


        val viewModel = PersonalViewModel(mockUseCase)

        val state = viewModel.uiState.value as? PersonalUiState.HasDetails
        assertNotNull("Current state not expected", state)
        assertEquals(expectedDetails, state?.movieDetails)
    }

    @Test
    fun fetchMovieDetailsError() = runTest {
        val errorMessage = "Failed to fetch person details"

        val mockUseCase = mockk<GetPopularPersonUserCase>()
        coEvery { mockUseCase.invoke() } returns Result.failure(Exception(errorMessage))


        val viewModel = PersonalViewModel(mockUseCase)

        val state = viewModel.uiState.value as? PersonalUiState.Empty
        assertNotNull("Current state not expected", state)
        assertEquals(errorMessage, state?.errorMessage)
        assertEquals(false, state?.isLoading)
    }


}
