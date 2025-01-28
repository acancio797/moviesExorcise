package com.exorcise.domain.repository

import com.exorcise.domain.model.MovieDetails
import com.exorcise.domain.model.MovieSummary
import com.exorcise.domain.model.TypeMovieOrder


interface MoviesRepository {
    suspend fun fetchPopularMovies(page: Int,type: TypeMovieOrder): Result<List<MovieSummary>>

    suspend fun fetchMovieDetails(id: Int): Result<MovieDetails>
    suspend fun fetchLocal(): Result<List<MovieSummary>>
}
