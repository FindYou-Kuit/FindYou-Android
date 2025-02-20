package com.example.findu.domain.usecase

import com.example.findu.domain.model.CheckEmailData
import com.example.findu.domain.repository.AuthRepository

class PostCheckEmailUseCase(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String): Result<CheckEmailData> =
        authRepository.postCheckEmail(email = email)
}