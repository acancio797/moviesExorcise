package com.exorcise.data.utils

import com.exorcise.domain.model.MapPoint
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.type.DateTime

fun DocumentSnapshot.toMovieGeolocation(): MapPoint? {
    // Ensure the document contains data
    val data = this.data ?: return null

    // Safely extract the GeoPoint from Firestore and convert to LatLng
    val geoPoint = data["position"] as? GeoPoint
    val latLng = geoPoint?.let { LatLng(it.latitude, it.longitude) }

    // Safely extract the timestamp and convert to DateTime
    val timestamp = data["time"] as? com.google.firebase.Timestamp
    val dateTime = timestamp?.toDate()?.let {
        DateTime.newBuilder().setSeconds((it.time / 1000).toInt()).build()
    }

    // Construct and return the MapPoint
    return MapPoint(
        id = this.id, // `id` is a property of `DocumentSnapshot`
        position = latLng,
        time = dateTime
    )
}

