package com.exorcise.movie.data.image

import com.exorcise.movie.model.ImageFile

interface ImageRepository {
    suspend fun uploadImage(imageFile: ImageFile): String?
}