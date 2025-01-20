package com.exorcise.domain.usecase

import com.exorcise.domain.model.ImageFile


interface UploadImageUseCase {
    suspend operator fun invoke(imageFile: ImageFile): String?
}
