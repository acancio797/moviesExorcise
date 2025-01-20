package com.exorcise.domain.model

import com.google.firebase.firestore.GeoPoint
import com.google.type.DateTime

data class MovieGeolocation(
    val id: String = "",
    val dateTime: DateTime,
    val position: GeoPoint? = null
)