package com.exorcise.data.data.image

import com.exorcise.data.data.firebase.MoviesFirebaseDataSource
import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.repository.ImageRepository
import javax.inject.Inject


class ImageRepositoryImpl @Inject constructor(private val moviesFirebaseDataSource: MoviesFirebaseDataSource) :
    ImageRepository {
    override suspend fun uploadImage(imageFile: ImageFile): String? {
        val result = moviesFirebaseDataSource.updateMoviesGeolocations(imageFile)
        return result
    }
}