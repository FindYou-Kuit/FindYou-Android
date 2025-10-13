package com.example.findu.domain.usecase

import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.UserInfoRepository

class PostGuestLoginUseCase(
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) {
    suspend fun postGuestLogin(): Result<GuestLoginData> = authRepository.postGuestLogin(
        deviceId = userInfoRepository.getDeviceId()
    )
}