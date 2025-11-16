package com.kuit.findu.domain.usecase.token

import com.kuit.findu.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetRefreshTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(refreshToken: String) = tokenRepository.setRefreshToken(refreshToken = refreshToken)
}