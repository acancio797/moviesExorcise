package com.exorcise.movie.utils

import com.exorcise.movie.model.MovieGeolocation
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint

fun DocumentSnapshot.toMovieGeolocation(): MovieGeolocation? {
    val data = this.data ?: return null

    val idMovie = data["idMovie"] as? Number
    val image = data["image"] as? String
    val latitude = data["latitude"] as? Number
    val longitude = data["longitude"] as? Number

    return MovieGeolocation(
        id = this.id,
        idMovie = idMovie?.toInt() ?: 0,
        image = image ?: "",
        position = GeoPoint(latitude?.toDouble() ?: 0.0, longitude?.toDouble() ?: 0.0),
    )
}