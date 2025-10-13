package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class PatchProfileImageUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend fun upload(
        imagePath: String? = null,
        defaultProfileImageName: String? = null
    ) = myRepository.patchProfileImage(
        imagePath = imagePath,
        defaultProfileImageName = defaultProfileImageName
    )
}