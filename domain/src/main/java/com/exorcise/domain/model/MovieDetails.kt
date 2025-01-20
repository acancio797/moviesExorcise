package com.exorcise.domain.model

import java.util.*


data class MovieDetails(
    val id: Int,
    val title: String,
    val runtime: Int,
    val tagline: String,
    val overview: String,
    val releaseDate: Date,
    val voteAverage: Float,
    val genres: List<String>,
    val backdropUrl: String,
    val posterUrl: String
)

enum class TypeMovieOrder(
    val messageOfTex: String
) {
    Popular("popular"),
    TopRated("top_rated"),
    Upcoming("upcoming");

    fun getMessage(): String {
        return messageOfTex
    }
}
