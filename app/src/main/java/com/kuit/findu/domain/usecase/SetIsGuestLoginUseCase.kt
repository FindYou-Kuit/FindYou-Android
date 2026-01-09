package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.repository.UserInfoRepository
import javax.inject.Inject

class SetIsGuestLoginUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(isGuest: Boolean) {
        userInfoRepository.setIsGuestLogin(isGuest)
    }
}
