package com.exorcise.domain.repository

import com.exorcise.domain.model.ImageFile


interface ImageRepository {
    suspend fun uploadImage(imageFile: ImageFile): String?
}