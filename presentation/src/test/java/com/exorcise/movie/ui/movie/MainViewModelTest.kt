package com.exorcise.movie.ui.movie


import com.exorcise.domain.model.MovieSummary
import com.exorcise.domain.model.TypeMovieOrder
import com.exorcise.domain.repository.MoviesRepository
import com.exorcise.movie.ui.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest : BaseViewModelTest() {

    @Test
    fun refreshMoviesSuccess() = runTest {
        val expected = listOf(
            MovieSummary(
                id = 0,
                title = "Sonic the Hedgehog 2",
                imageUrl = "http://image.com/sonic.jpg",
                rating = 7.0f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 30).time
            ),
            MovieSummary(
                id = 1,
                title = "The Batman",
                imageUrl = "http://image.com/batman.jpg",
                rating = 8.0f,
                releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time
            ),
        )

        val mockMoviesRepo = mockk<MoviesRepository> {
            coEvery { fetchPopularMovies(1, TypeMovieOrder.Popular) } returns Result.success(
                expected
            )
            coEvery { fetchLocal() } returns Result.success(expected)
        }

        val homeViewModel = MovieViewModel(mockMoviesRepo)

        val state = homeViewModel.uiState.value as? MovieUiState.HasMovies
        assertNotNull("Current state not expected", state)
        assertEquals(expected, state?.moviesFeed)
        assertEquals(emptyList<String>(), state?.errorMessages)
        assertEquals(false, state?.isRefreshing)
    }

    @Test
    fun refreshMoviesError() = runTest {
        val errorMessage = "some error"
        val expected = listOf(errorMessage)

        val mockMoviesRepo = mockk<MoviesRepository> {
            coEvery { fetchPopularMovies(1, TypeMovieOrder.Popular) } returns Result.failure(
                Throwable(errorMessage)
            )
            coEvery { fetchLocal() } returns Result.failure(Throwable(errorMessage))
        }

        val homeViewModel = MovieViewModel(mockMoviesRepo)

        val state = homeViewModel.uiState.value
        assertEquals(expected, state.errorMessages)
        assertEquals(false, state.isRefreshing)
    }
}
