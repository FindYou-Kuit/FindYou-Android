package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class PatchProfileImageUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend fun uploadFile(imagePath: String) =
        myRepository.patchProfileImageFile(imagePath)

    suspend fun uploadDefault(defaultProfileImageName: String) =
        myRepository.patchProfileImageDefault(defaultProfileImageName)
}