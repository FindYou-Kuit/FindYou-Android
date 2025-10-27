package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.repository.UserInfoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetDeviceIdUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(deviceId: String) = userInfoRepository.setDeviceId(deviceId = deviceId)
}