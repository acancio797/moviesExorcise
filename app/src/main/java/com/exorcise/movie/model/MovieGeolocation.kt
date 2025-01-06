package com.exorcise.movie.model

import com.google.firebase.firestore.GeoPoint

data class MovieGeolocation (
    val id: String = "",
    val idMovie: Int = 0,
    val image: String = "",
    val position: GeoPoint? = null
)