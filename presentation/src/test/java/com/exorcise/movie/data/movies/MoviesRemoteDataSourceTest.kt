package com.exorcise.movie.data.movies

import com.exorcise.data.api.MoviesApiClient
import com.exorcise.data.api.responses.MovieResponse
import com.exorcise.data.api.responses.PopularMovie
import com.exorcise.data.api.responses.PopularMoviesResponse
import com.exorcise.data.data.movies.MoviesRemoteDataSource
import com.exorcise.data.local.MovieDao
import com.exorcise.domain.model.TypeMovieOrder
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRemoteDataSourceTest {

    @Test
    fun getPopularMovies() = runTest {
        val popularMoviesResponse = PopularMoviesResponse(
            page = 1,
            popularMovies = listOf(
                PopularMovie(
                    adult = false,
                    backdropPath = "/sonic-backdropPath.jpg",
                    genreIds = listOf(28, 878, 35, 10751, 12),
                    id = 0,
                    originalLanguage = "en",
                    originalTitle = "Sonic the Hedgehog 2",
                    overview = "Sonic overview.",
                    popularity = 10.0,
                    posterPath = "/sonic-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 30).time,
                    title = "Sonic the Hedgehog 2",
                    video = false,
                    voteAverage = 7.7f,
                    voteCount = 1000
                ),
                PopularMovie(
                    adult = false,
                    backdropPath = "/batman-backdropPath.jpg",
                    genreIds = listOf(80, 9648, 53),
                    id = 1,
                    originalLanguage = "en",
                    originalTitle = "The Batman",
                    overview = "Batman overview",
                    popularity = 3.0,
                    posterPath = "/batman-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                    title = "The Batman",
                    video = false,
                    voteAverage = 7.8f,
                    voteCount = 2000
                ),
            ),
            totalPages = 1,
            totalResults = 2
        )
        val expected = Result.success(popularMoviesResponse)

        val pageToFetch = 1
        val type = TypeMovieOrder.Popular
        val mockMoviesApiClient = mockk<MoviesApiClient> {
            coEvery { getPopularMovies(pageToFetch) } returns Result.success(popularMoviesResponse)
        }
        val mockMoviesDao = mockk<MovieDao> {}

        val moviesRemoteDataSource =
            MoviesRemoteDataSource(
                mockMoviesApiClient,
                mockMoviesDao,
                UnconfinedTestDispatcher(testScheduler)
            )

        assertEquals(expected, moviesRemoteDataSource.getPopularMovies(pageToFetch, type))
    }

    @Test
    fun getUpcomingMovies() = runTest {
        val popularMoviesResponse = PopularMoviesResponse(
            page = 1,
            popularMovies = listOf(
                PopularMovie(
                    adult = false,
                    backdropPath = "/sonic-backdropPath.jpg",
                    genreIds = listOf(28, 878, 35, 10751, 12),
                    id = 0,
                    originalLanguage = "en",
                    originalTitle = "Sonic the Hedgehog 2",
                    overview = "Sonic overview.",
                    popularity = 10.0,
                    posterPath = "/sonic-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 30).time,
                    title = "Sonic the Hedgehog 2",
                    video = false,
                    voteAverage = 7.7f,
                    voteCount = 1000
                ),
                PopularMovie(
                    adult = false,
                    backdropPath = "/batman-backdropPath.jpg",
                    genreIds = listOf(80, 9648, 53),
                    id = 1,
                    originalLanguage = "en",
                    originalTitle = "The Batman",
                    overview = "Batman overview",
                    popularity = 3.0,
                    posterPath = "/batman-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                    title = "The Batman",
                    video = false,
                    voteAverage = 7.8f,
                    voteCount = 2000
                ),
            ),
            totalPages = 1,
            totalResults = 2
        )
        val expected = Result.success(popularMoviesResponse)

        val pageToFetch = 1
        val type = TypeMovieOrder.Upcoming
        val mockMoviesApiClient = mockk<MoviesApiClient> {
            coEvery { getPopularUpcoming(pageToFetch) } returns Result.success(popularMoviesResponse)
        }
        val mockMoviesDao = mockk<MovieDao> {}

        val moviesRemoteDataSource =
            MoviesRemoteDataSource(
                mockMoviesApiClient,
                mockMoviesDao,
                UnconfinedTestDispatcher(testScheduler)
            )

        assertEquals(expected, moviesRemoteDataSource.getPopularMovies(pageToFetch, type))
    }

    @Test
    fun getTopRatedMovies() = runTest {
        val popularMoviesResponse = PopularMoviesResponse(
            page = 1,
            popularMovies = listOf(
                PopularMovie(
                    adult = false,
                    backdropPath = "/sonic-backdropPath.jpg",
                    genreIds = listOf(28, 878, 35, 10751, 12),
                    id = 0,
                    originalLanguage = "en",
                    originalTitle = "Sonic the Hedgehog 2",
                    overview = "Sonic overview.",
                    popularity = 10.0,
                    posterPath = "/sonic-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 30).time,
                    title = "Sonic the Hedgehog 2",
                    video = false,
                    voteAverage = 7.7f,
                    voteCount = 1000
                ),
                PopularMovie(
                    adult = false,
                    backdropPath = "/batman-backdropPath.jpg",
                    genreIds = listOf(80, 9648, 53),
                    id = 1,
                    originalLanguage = "en",
                    originalTitle = "The Batman",
                    overview = "Batman overview",
                    popularity = 3.0,
                    posterPath = "/batman-poster.jpg",
                    releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
                    title = "The Batman",
                    video = false,
                    voteAverage = 7.8f,
                    voteCount = 2000
                ),
            ),
            totalPages = 1,
            totalResults = 2
        )
        val expected = Result.success(popularMoviesResponse)

        val pageToFetch = 1
        val type = TypeMovieOrder.TopRated
        val mockMoviesApiClient = mockk<MoviesApiClient> {
            coEvery { getTopRatedMovies(pageToFetch) } returns Result.success(popularMoviesResponse)
        }
        val mockMoviesDao = mockk<MovieDao> {}

        val moviesRemoteDataSource =
            MoviesRemoteDataSource(
                mockMoviesApiClient,
                mockMoviesDao,
                UnconfinedTestDispatcher(testScheduler)
            )

        assertEquals(expected, moviesRemoteDataSource.getPopularMovies(pageToFetch, type))
    }

    @Test
    fun getMovieDetails() = runTest {
        val movieDetailsResponse = MovieResponse(
            id = 1,
            title = "The Batman",
            tagline = "The Batman tagline",
            overview = "The Batman Overview",
            runtime = 240,
            voteAverage = 7.8f,
            releaseDate = GregorianCalendar(2022, Calendar.MARCH, 1).time,
            genres = listOf(
                MovieResponse.Genre(id = 1, name = "Action"),
                MovieResponse.Genre(id = 2, name = "Crime")
            ),
            posterPath = "/batman-poster.jpg",
            backdropPath = "/batman-backdropPath.jpg",
            adult = false,
            belongsToCollection = false,
            budget = 0,
            homepage = "",
            imdbId = "",
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            revenue = 0,
            spokenLanguages = emptyList(),
            status = "",
            video = false,
            voteCount = 0
        )
        val expected = Result.success(movieDetailsResponse)

        val mockMoviesApiClient = mockk<MoviesApiClient> {
            coEvery { getMovieDetails(movieDetailsResponse.id) } returns Result.success(
                movieDetailsResponse
            )
        }

        val mockMoviesDao = mockk<MovieDao> {}

        val moviesRemoteDataSource =
            MoviesRemoteDataSource(
                mockMoviesApiClient,
                mockMoviesDao,
                UnconfinedTestDispatcher(testScheduler)
            )

        assertEquals(expected, moviesRemoteDataSource.getMovieDetails(movieDetailsResponse.id))
    }
}
