package com.example.findu.domain.usecase.token

import com.example.findu.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(accessToken: String) = tokenRepository.setAccessToken(accessToken = accessToken)
}