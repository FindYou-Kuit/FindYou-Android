package com.example.findu.data.dataremote.datasource

import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun postLogin(
        email: String,
        password: String
    ): Response<Unit>
}