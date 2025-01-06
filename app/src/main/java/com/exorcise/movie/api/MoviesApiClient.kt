package com.exorcise.movie.api

import com.exorcise.movie.api.responses.ConfigurationResponse
import com.exorcise.movie.api.responses.MovieResponse
import com.exorcise.movie.api.responses.PopularMoviesResponse
import com.exorcise.movie.api.responses.PopularTvResponse

interface MoviesApiClient {
    suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    suspend fun getPopularMovies(page: Int): Result<PopularMoviesResponse>
    suspend fun getPopularSeries(page: Int): Result<PopularTvResponse>

    suspend fun getMovieDetails(id: Int): Result<MovieResponse>
}
