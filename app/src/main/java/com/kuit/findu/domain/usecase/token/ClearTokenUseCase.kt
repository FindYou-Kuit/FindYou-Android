package com.kuit.findu.domain.usecase.token

import com.kuit.findu.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearTokenUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke() = tokenRepository.clearToken()
}