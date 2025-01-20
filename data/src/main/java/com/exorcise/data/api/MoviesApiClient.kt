package com.exorcise.data.api

import com.exorcise.data.api.responses.ConfigurationResponse
import com.exorcise.data.api.responses.MovieResponse
import com.exorcise.data.api.responses.PersonDetailResponse
import com.exorcise.data.api.responses.PopularMoviesResponse
import com.exorcise.data.api.responses.PopularPersonResponse
import com.exorcise.data.api.responses.PopularTvResponse


interface MoviesApiClient {
    suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    suspend fun getPopularMovies(page: Int): Result<PopularMoviesResponse>
    suspend fun getTopRatedMovies(page: Int): Result<PopularMoviesResponse>

    suspend fun getPopularUpcoming(page: Int): Result<PopularMoviesResponse>
    suspend fun getPopularSeries(page: Int): Result<PopularTvResponse>


    suspend fun getMovieDetails(id: Int): Result<MovieResponse>

    suspend fun getPopularPerson(page: Int): Result<PopularPersonResponse>

    suspend fun getPersonDetails(id: Int): Result<PersonDetailResponse>
}
