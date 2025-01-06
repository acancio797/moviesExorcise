package com.exorcise.movie.firebase

import com.exorcise.movie.model.MovieGeolocation
import com.exorcise.movie.utils.toMap
import com.exorcise.movie.utils.toMovieGeolocation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MoviesFirebaseClient @Inject constructor(
    private val db: FirebaseFirestore,
) {
    suspend fun getMoviesGeo(): List<MovieGeolocation?> {
        val snapshot = db.collection("movies")
            .get()
            .await()

        val result = mutableListOf<MovieGeolocation?>()

        for (document in snapshot.documents) {
            result.add(document.toMovieGeolocation())
        }

        return result
    }

    suspend fun insertMovie(movie: MovieGeolocation) {
        val dataMap = movie.toMap()
             db.collection("movies")
            .add(dataMap)
            .await()

    }
}