package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.model.GuestLoginData
import com.kuit.findu.domain.repository.AuthRepository
import com.kuit.findu.domain.repository.UserInfoRepository

class PostGuestLoginUseCase(
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) {
    suspend fun postGuestLogin(): Result<GuestLoginData> = authRepository.postGuestLogin(
        deviceId = userInfoRepository.getDeviceId()
    )
}