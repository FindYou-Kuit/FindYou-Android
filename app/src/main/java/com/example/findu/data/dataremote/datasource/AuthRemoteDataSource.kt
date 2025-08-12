package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.response.CheckEmailResponseDto
import com.example.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun postLogin(
        loginRequestDto: LoginRequestDto
    ): BaseResponse<LoginResponseDto>

    suspend fun postGuestLogin(
        guestLoginRequestDto: GuestLoginRequestDto
    ): BaseResponse<GuestLoginResponseDto>

    suspend fun postCheckEmail(
        email: String
    ): BaseResponse<CheckEmailResponseDto>

    suspend fun postSignup(
        email: String,
        password: String,
        nickname: String
    ): Response<Unit>
}