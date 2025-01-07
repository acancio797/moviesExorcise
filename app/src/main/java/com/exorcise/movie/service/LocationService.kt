package com.exorcise.movie.service

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.exorcise.movie.data.location.LocationRepository
import com.exorcise.movie.model.MapPoint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.type.DateTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var repository: LocationRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        getCurrentLocation()
        return START_STICKY
    }

    private fun getCurrentLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    saveLocation(it)
                }
            }
        } catch (ex: SecurityException) {

        }

    }

    private fun saveLocation(location: Location) {
        serviceScope.launch {
            val calendario = Calendar.getInstance(TimeZone.getDefault())
            val dateTime = DateTime.newBuilder()
                .setYear(calendario.get(Calendar.YEAR))
                .setMonth(calendario.get(Calendar.MONTH) + 1)
                .setDay(calendario.get(Calendar.DAY_OF_MONTH))
                .setHours(calendario.get(Calendar.HOUR_OF_DAY))
                .setMinutes(calendario.get(Calendar.MINUTE))
                .setSeconds(calendario.get(Calendar.SECOND))
                .build()
            try {
                repository.saveLocation(
                    MapPoint(
                        LatLng(location.latitude, location.longitude),
                        dateTime,
                        ""
                    )
                )
                sendNotification(
                    "Location Saved",
                    "Location (${location.latitude}, ${location.longitude}) saved successfully."
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
                sendNotification("Error", "Failed to save location.")
            }
        }

    }

    private fun sendNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(this, "LOCATION_CHANNEL")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
