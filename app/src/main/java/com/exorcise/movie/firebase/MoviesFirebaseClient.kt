package com.exorcise.movie.firebase

import com.exorcise.movie.model.MapPoint
import com.exorcise.movie.model.MovieGeolocation
import com.exorcise.movie.utils.toMap
import com.exorcise.movie.utils.toMovieGeolocation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MoviesFirebaseClient @Inject constructor(
    private val db: FirebaseFirestore,
) {
    suspend fun getMoviesGeo(): List<MapPoint?> {
        val snapshot = db.collection("movies")
            .get()
            .await()

        val result = mutableListOf<MapPoint?>()

        for (document in snapshot.documents) {
            result.add(document.toMovieGeolocation())
        }

        return result
    }

    suspend fun insertMovie(movie: MapPoint) {
        val dataMap = movie.toMap()
             db.collection("movies")
            .add(dataMap)
            .await()

    }
}