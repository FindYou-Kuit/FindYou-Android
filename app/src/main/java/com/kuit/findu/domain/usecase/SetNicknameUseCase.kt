package com.kuit.findu.domain.usecase

import com.kuit.findu.domain.repository.UserInfoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetNicknameUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(nickname: String) = userInfoRepository.setNickname(nickname = nickname)
}