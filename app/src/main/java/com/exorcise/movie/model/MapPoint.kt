package com.exorcise.movie.model

import com.google.android.gms.maps.model.LatLng
import com.google.type.DateTime

data class MapPoint(
    val position: LatLng,
    val time:DateTime?,
    val id:String
)
