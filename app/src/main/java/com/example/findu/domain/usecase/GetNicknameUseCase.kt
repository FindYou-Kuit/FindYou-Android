package com.example.findu.domain.usecase

import com.example.findu.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetNicknameUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke() = userInfoRepository.getNickname()
}