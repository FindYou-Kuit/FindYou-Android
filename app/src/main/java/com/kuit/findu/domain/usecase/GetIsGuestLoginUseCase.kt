package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetIsGuestLoginUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(): Boolean {
        return userInfoRepository.getIsGuestLogin()
    }
}
