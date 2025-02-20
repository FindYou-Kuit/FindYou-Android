package com.example.findu.domain.usecase

import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.TokenRepository

class PostSignupUseCase(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun postSignup(
        email: String,
        password: String,
        nickname: String
    ): Result<Unit> =
        authRepository.postSignup(email, password, nickname).mapCatching { accessToken ->
            tokenRepository.setTokens(accessToken)
        }
}