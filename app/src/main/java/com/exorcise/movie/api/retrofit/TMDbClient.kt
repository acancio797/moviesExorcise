package com.exorcise.movie.api.retrofit

import com.exorcise.movie.api.MoviesApiClient
import com.exorcise.movie.api.responses.ConfigurationResponse
import com.exorcise.movie.api.responses.MovieResponse
import com.exorcise.movie.api.responses.PopularMoviesResponse
import com.exorcise.movie.api.responses.PopularTvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbClient : MoviesApiClient {

    @GET("configuration")
    override suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    @GET("movie/popular")
    override suspend fun getPopularMovies(@Query("page") page: Int): Result<PopularMoviesResponse>

    @GET("tv/popular")
    override suspend fun getPopularSeries(@Query("page") page: Int): Result<PopularTvResponse>

    @GET("movie/{id}")
    override suspend fun getMovieDetails(@Path("id") id: Int): Result<MovieResponse>
}
