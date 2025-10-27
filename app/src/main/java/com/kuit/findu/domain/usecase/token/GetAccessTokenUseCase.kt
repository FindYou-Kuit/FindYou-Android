package com.kuit.findu.domain.usecase.token

import com.kuit.findu.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAccessTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke() = tokenRepository.getAccessToken()
}