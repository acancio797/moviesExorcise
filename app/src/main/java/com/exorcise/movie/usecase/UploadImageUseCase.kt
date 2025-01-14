package com.exorcise.movie.usecase

import com.exorcise.movie.data.image.ImageRepository
import com.exorcise.movie.model.ImageFile
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {

    suspend operator fun invoke(imageFile: ImageFile): String? {
        return repository.uploadImage(imageFile)
    }
}
