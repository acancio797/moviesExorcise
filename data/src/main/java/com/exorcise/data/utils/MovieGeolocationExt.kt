package com.exorcise.data.utils

import com.exorcise.domain.model.MapPoint
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint


fun MapPoint.toMap(): Map<String, Any?> {

    return mapOf(
        "time" to this.time.toString(),
        "position" to this.position?.toGeoPoint()
    )
}

fun LatLng.toGeoPoint(): GeoPoint {
    return GeoPoint(this.latitude, this.longitude)
}