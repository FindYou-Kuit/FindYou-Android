package com.example.findu.domain.usecase.my

import com.example.findu.domain.repository.MyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PatchProfileImageUseCase @Inject constructor(
    private val myRepository: MyRepository
) {
    suspend fun uploadFile(profileImageFile: MultipartBody.Part) =
        myRepository.patchProfileImageFile(profileImageFile)

    suspend fun uploadDefault(defaultProfileImageName: RequestBody) =
        myRepository.patchProfileImageDefault(defaultProfileImageName)
}