package com.example.findu.domain.usecase

import com.example.findu.domain.repository.DeviceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDeviceIdUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    operator fun invoke(deviceId: String) = deviceRepository.setDeviceId(deviceId = deviceId)
}