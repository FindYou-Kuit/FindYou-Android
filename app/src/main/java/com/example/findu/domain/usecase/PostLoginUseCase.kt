package com.example.findu.domain.usecase

import com.example.findu.domain.repository.AuthRepository
import com.example.findu.domain.repository.TokenRepository

class PostLoginUseCase(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun postLogin(
        email: String,
        password: String
    ): Result<Unit> = authRepository.postLogin(
        email = email,
        password = password
    ).mapCatching { accessToken ->
        tokenRepository.setTokens(accessToken)
    }
}