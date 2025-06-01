package com.example.findu.domain.usecase.token

import com.example.findu.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRefreshTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke() = tokenRepository.getRefreshToken()
}