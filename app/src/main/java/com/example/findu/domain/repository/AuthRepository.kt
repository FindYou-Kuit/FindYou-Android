package com.example.findu.domain.repository

interface AuthRepository {
    suspend fun postLogin(
        email: String,
        password: String
    ): Result<String>
}