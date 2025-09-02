package com.example.findu.domain.usecase

import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.LoginInfo
import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.TokenRepository

class PostLoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun postLogin(
        loginInfo:LoginInfo
    ): Result<LoginData> = authRepository.postLogin(
        loginInfo = loginInfo
    )
}