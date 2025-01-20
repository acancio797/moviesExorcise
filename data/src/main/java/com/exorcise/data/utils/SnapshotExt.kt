package com.exorcise.data.utils

import com.exorcise.domain.model.MapPoint
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.type.DateTime

fun DocumentSnapshot.toMovieGeolocation(): MapPoint? {
    val data = this.data ?: return null


    val latLng = data["position"] as? GeoPoint
    val dateTime = data["time"] as? DateTime

    return MapPoint(
        id = this.id,
        time = dateTime,
        position = LatLng(latLng?.latitude ?: 0.0, latLng?.longitude ?: 0.0),
    )
}