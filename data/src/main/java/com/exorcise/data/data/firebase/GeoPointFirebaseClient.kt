package com.exorcise.data.data.firebase

import com.exorcise.data.utils.toMap
import com.exorcise.data.utils.toMovieGeolocation
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.model.MapPoint
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

