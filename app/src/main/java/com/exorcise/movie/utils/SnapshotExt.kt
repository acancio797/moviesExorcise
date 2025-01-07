package com.exorcise.movie.utils

import com.exorcise.movie.model.MapPoint
import com.exorcise.movie.model.MovieGeolocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.type.DateTime

fun DocumentSnapshot.toMovieGeolocation(): MapPoint? {
    val data = this.data ?: return null

    val latitude = data["latitude"] as? Number
    val longitude = data["longitude"] as? Number
    val dateTime = data["time"] as? DateTime

    return MapPoint(
        id = this.id,
        time = dateTime,
        position = LatLng(latitude?.toDouble() ?: 0.0, longitude?.toDouble() ?: 0.0),
    )
}