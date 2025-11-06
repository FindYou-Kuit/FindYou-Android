package com.example.findu.domain.usecase.my

import com.example.findu.domain.model.my.MyProfileImageUpdate
import com.example.findu.domain.repository.MyRepository
import javax.inject.Inject

class PatchProfileImageUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend fun upload(update: MyProfileImageUpdate): Result<Unit> =
        myRepository.patchProfileImage(update)
}