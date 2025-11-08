package com.kuit.findu.domain.usecase.auth

import com.kuit.findu.domain.repository.AuthRepository

class PostCheckNicknameUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(nickname: String): Result<Boolean> =
        authRepository.postCheckNickname(nickname = nickname)
}