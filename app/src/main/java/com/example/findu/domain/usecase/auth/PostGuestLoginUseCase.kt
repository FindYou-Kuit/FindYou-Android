package com.example.findu.domain.usecase.auth

import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.DeviceRepository

class PostGuestLoginUseCase(
    private val authRepository: AuthRepository,
    private val deviceRepository: DeviceRepository
) {
    suspend fun postGuestLogin(): Result<GuestLoginData> = authRepository.postGuestLogin(
        deviceId = deviceRepository.getDeviceId()
    )
}