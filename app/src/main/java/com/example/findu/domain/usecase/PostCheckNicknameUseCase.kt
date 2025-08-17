package com.example.findu.domain.usecase

import com.example.findu.domain.repository.AuthRepository

class PostCheckNicknameUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(nickname: String): Result<Boolean> =
        authRepository.postCheckNickname(nickname = nickname)
}