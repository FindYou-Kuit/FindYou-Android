package com.example.findu.domain.usecase

import com.example.findu.domain.model.UserInfo
import com.example.findu.domain.repository.AuthRepository
import java.io.File

class PostSignupUseCase(
    private val authRepository: AuthRepository
) {
    suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long
    ): Result<UserInfo> = authRepository.postSignup(
        profileImageFile = profileImageFile,
        defaultImageName = if (profileImageFile != null) {
            null
        } else defaultImageName,
        nickname = nickname,
        kakaoId = kakaoId
    )

}