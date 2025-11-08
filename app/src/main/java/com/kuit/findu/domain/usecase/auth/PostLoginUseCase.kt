package com.kuit.findu.domain.usecase.auth

import com.kuit.findu.domain.model.LoginData
import com.kuit.findu.domain.model.LoginInfo
import com.kuit.findu.domain.repository.AuthRepository
import com.kuit.findu.domain.repository.UserInfoRepository

class PostLoginUseCase(
    private val authRepository: AuthRepository,
    private val userInfoRepository: UserInfoRepository
) {
    suspend fun postLogin(
        kakaoId:Long
    ): Result<LoginData> = authRepository.postLogin(
        loginInfo = LoginInfo(
            kakaoId = kakaoId,
            deviceId = userInfoRepository.getDeviceId()
        )
    )
}