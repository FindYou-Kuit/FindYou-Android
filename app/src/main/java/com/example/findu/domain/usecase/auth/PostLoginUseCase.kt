package com.example.findu.domain.usecase.auth

import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.LoginInfo
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.DeviceRepository

class PostLoginUseCase(
    private val authRepository: AuthRepository,
    private val deviceRepository: DeviceRepository
) {
    suspend fun postLogin(
        kakaoId:Long
    ): Result<LoginData> = authRepository.postLogin(
        loginInfo = LoginInfo(
            kakaoId = kakaoId,
            deviceId = deviceRepository.getDeviceId()
        )
    )
}