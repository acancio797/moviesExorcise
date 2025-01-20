package com.exorcise.domain.usecase.impl

import com.exorcise.domain.model.ImageFile
import com.exorcise.domain.repository.ImageRepository
import com.exorcise.domain.usecase.UploadImageUseCase
import javax.inject.Inject

class UploadImageUseCaseImpl @Inject constructor(
    private val repository: ImageRepository
) : UploadImageUseCase {

    override suspend operator fun invoke(imageFile: ImageFile): String? {
        return repository.uploadImage(imageFile)
    }
}