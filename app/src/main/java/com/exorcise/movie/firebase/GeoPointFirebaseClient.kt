package com.exorcise.movie.firebase

import com.exorcise.movie.model.ImageFile
import com.exorcise.movie.model.MapPoint
import com.exorcise.movie.utils.toMap
import com.exorcise.movie.utils.toMovieGeolocation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GeoPointFirebaseClient @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    suspend fun getMoviesGeo(): List<MapPoint?> {
        val snapshot = db.collection("movie")
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

        db.collection("movie")
            .add(dataMap)
            .await()

    }

    suspend fun uploadImageToFirebase(imageFile: ImageFile): String? {
        val imageRef = storage.reference.child("images/${imageFile.fileName}")
        val result = imageRef.putFile(imageFile.uri).await()

        return result.uploadSessionUri?.path
    }
}

