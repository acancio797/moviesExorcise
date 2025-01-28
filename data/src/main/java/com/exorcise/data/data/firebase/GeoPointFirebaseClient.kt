package com.exorcise.data.data.firebase

import com.exorcise.data.utils.toMap
import com.exorcise.data.utils.toMovieGeolocation
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.model.MapPoint
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import javax.inject.Inject

class GeoPointFirebaseClient @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    suspend fun getMoviesGeo(): List<MapPoint?> = suspendCancellableCoroutine { continuation ->
        db.collection("movie")
            .get()
            .addOnSuccessListener { snapshot ->
                val result = snapshot.documents.map { it.toMovieGeolocation() }
                continuation.resume(result)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun insertMovie(movie: MapPoint) = suspendCancellableCoroutine<Unit> { continuation ->
        val dataMap = movie.toMap()
        db.collection("movie")
            .add(dataMap)
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun uploadImageToFirebase(imageFile: ImageFile): String? =
        suspendCancellableCoroutine { continuation ->
            val imageRef = storage.reference.child("images/${imageFile.fileName}")
            imageRef.putFile(imageFile.uri)
                .addOnSuccessListener { taskSnapshot ->
                    continuation.resume(taskSnapshot.uploadSessionUri?.path)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}
