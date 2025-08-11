package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.CheckEmailRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.request.SignupRequestDto
import com.example.findu.data.dataremote.model.response.CheckEmailResponseDto
import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.data.dataremote.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun postLogin(loginRequestDto: LoginRequestDto): BaseResponse<LoginResponseDto> =
        authService.postLogin(loginRequestDto=loginRequestDto)

    override suspend fun postCheckEmail(email: String): BaseResponse<CheckEmailResponseDto> =
        authService.postCheckEmail(CheckEmailRequestDto(email))

    override suspend fun postSignup(
        email: String,
        password: String,
        nickname: String
    ): Response<Unit> = authService.postSignup(SignupRequestDto(email, password, nickname))
}