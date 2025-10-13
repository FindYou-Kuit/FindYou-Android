package com.example.findu.domain.usecase.auth

import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.UserInfoRepository

class PostGuestLoginUseCase(
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) {
    suspend fun postGuestLogin(): Result<GuestLoginData> = authRepository.postGuestLogin(
//        deviceId = userInfoRepository.getDeviceId()
        deviceId = "8483f57930bb6a8a"

    )
}