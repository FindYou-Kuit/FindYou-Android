package com.example.findu.domain.usecase

import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.LoginInfo
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.UserInfoRepository

class PostLoginUseCase(
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) {
    suspend fun postLogin(
        kakaoId:Long
    ): Result<LoginData> = authRepository.postLogin(
        loginInfo = LoginInfo(
            kakaoId = kakaoId,
            deviceId = userInfoRepository.getDeviceId()
        )
    )
}