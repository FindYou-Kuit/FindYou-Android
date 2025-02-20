package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.request.LoginRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login")
    suspend fun postLogin(
        @Body loginRequestBody: LoginRequestDto
    ): Response<Unit>
}