package com.example.findu.domain.repository

import com.example.findu.domain.model.CheckEmailData

interface AuthRepository {
    suspend fun postLogin(
        email: String,
        password: String
    ): Result<String>

    suspend fun postCheckEmail(
        email: String
    ): Result<CheckEmailData>

    suspend fun postSignup(
        email: String,
        password: String,
        nickname: String
    ): Result<String>
}