package com.example.findu.domain.usecase.token

import com.example.findu.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetRefreshTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(refreshToken: String) = tokenRepository.setRefreshToken(refreshToken = refreshToken)
}