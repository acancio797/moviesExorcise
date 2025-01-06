package com.exorcise.movie.utils

import com.exorcise.movie.model.MovieGeolocation
import com.google.firebase.firestore.GeoPoint

fun MovieGeolocation.toMap(): Map<String, Any> {

    return mapOf(
        "idMovie" to this.idMovie,
        "image" to this.image,
        "position" to (this.position ?: GeoPoint(0.0, 0.0))
    )
}