package com.exorcise.data.api.responses

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PopularTvResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val popularMovies: List<PopularTv>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class PopularTv(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    val id: Int,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("first_air_date") val firstAirDate: Date,
    val name: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)