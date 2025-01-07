package com.exorcise.movie.data.movies

import com.exorcise.movie.model.MovieSummary
import com.exorcise.movie.model.MovieDetails
import com.exorcise.movie.model.MovieGeolocation

interface MoviesRepository {
    suspend fun fetchPopularMovies(page: Int): Result<List<MovieSummary>>
    suspend fun fetchPopularTv(page: Int): Result<List<MovieSummary>>

    suspend fun fetchMovieDetails(id: Int): Result<MovieDetails>
    suspend fun fetchLocal(): Result<List<MovieSummary>>
}
