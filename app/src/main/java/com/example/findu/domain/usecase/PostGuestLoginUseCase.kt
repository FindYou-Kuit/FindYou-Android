package com.example.findu.domain.usecase

import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.repository.AuthRepository

class PostGuestLoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun postGuestLogin(
        deviceId:String
    ): Result<GuestLoginData> = authRepository.postGuestLogin(
        deviceId = deviceId
    )
}