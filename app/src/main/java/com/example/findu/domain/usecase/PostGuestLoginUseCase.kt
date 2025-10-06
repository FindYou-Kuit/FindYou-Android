package com.example.findu.domain.usecase

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.DeviceRepository

class PostGuestLoginUseCase(
    private val authRepository: AuthRepository,
    private val context: Context
) {
    @SuppressLint("HardwareIds")
    suspend fun postGuestLogin(): Result<GuestLoginData> {
        val deviceId = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: ""

        return authRepository.postGuestLogin(deviceId)
    }
}