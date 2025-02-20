package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.CheckEmailRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.request.SignupRequestDto
import com.example.findu.data.dataremote.model.response.CheckEmailResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login")
    suspend fun postLogin(
        @Body loginRequestBody: LoginRequestDto
    ): Response<Unit>

    @POST("/api/v1/auth/check/duplicate-email")
    suspend fun postCheckEmail(
        @Body checkEmailRequestDto: CheckEmailRequestDto
    ): BaseResponse<CheckEmailResponseDto>

    @POST("/api/v1/auth/signup")
    suspend fun postSignup(
        @Body signupRequestBody: SignupRequestDto
    ): Response<Unit>
}