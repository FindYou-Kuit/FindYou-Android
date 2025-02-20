package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.CheckEmailRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.response.CheckEmailResponseDto
import com.example.findu.data.dataremote.service.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun postLogin(email: String, password: String): Response<Unit> =
        authService.postLogin(LoginRequestDto(email, password))

    override suspend fun postCheckEmail(email: String): BaseResponse<CheckEmailResponseDto> =
        authService.postCheckEmail(CheckEmailRequestDto(email))
}