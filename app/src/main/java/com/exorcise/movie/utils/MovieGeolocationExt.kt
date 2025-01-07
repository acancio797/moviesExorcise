package com.exorcise.movie.utils

import com.exorcise.movie.model.MapPoint
import com.exorcise.movie.model.MovieGeolocation
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

fun MapPoint.toMap(): Map<String, Any> {

    return mapOf(
        "id" to this.id,
        "time" to this.time!!,
        "position" to this.position.toGeoPoint()
    )
}

fun LatLng.toGeoPoint(): GeoPoint {
    return GeoPoint(this.latitude, this.longitude)
}