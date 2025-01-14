package com.exorcise.movie.data.image

import com.exorcise.movie.firebase.MoviesFirebaseDataSource
import com.exorcise.movie.model.ImageFile

class ImageRepositoryImpl(private val moviesFirebaseDataSource: MoviesFirebaseDataSource) :

    ImageRepository {
    override suspend fun uploadImage(imageFile: ImageFile): String? {
        val result = moviesFirebaseDataSource.updateMoviesGeolocations(imageFile)
        return result
    }
}