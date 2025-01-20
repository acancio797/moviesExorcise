package com.exorcise.data.api.retrofit

import com.exorcise.data.api.MoviesApiClient
import com.exorcise.data.api.responses.ConfigurationResponse
import com.exorcise.data.api.responses.MovieResponse
import com.exorcise.data.api.responses.PersonDetailResponse
import com.exorcise.data.api.responses.PopularMoviesResponse
import com.exorcise.data.api.responses.PopularPersonResponse
import com.exorcise.data.api.responses.PopularTvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbClient : MoviesApiClient {

    @GET("configuration")
    override suspend fun getApiConfiguration(): Result<ConfigurationResponse>

    @GET("movie/popular")
    override suspend fun getPopularMovies(@Query("page") page: Int): Result<PopularMoviesResponse>

    @GET("movie/top_rated")
    override suspend fun getTopRatedMovies(@Query("page") page: Int): Result<PopularMoviesResponse>

    @GET("movie/upcoming")
    override suspend fun getPopularUpcoming(@Query("page") page: Int): Result<PopularMoviesResponse>

    @GET("tv/popular")
    override suspend fun getPopularSeries(@Query("page") page: Int): Result<PopularTvResponse>

    @GET("movie/{id}")
    override suspend fun getMovieDetails(@Path("id") id: Int): Result<MovieResponse>

    @GET("person/popular")
    override suspend fun getPopularPerson(@Query("page") page: Int): Result<PopularPersonResponse>

    @GET("person/{id}")
    override suspend fun getPersonDetails(@Path("id") id: Int): Result<PersonDetailResponse>
}
