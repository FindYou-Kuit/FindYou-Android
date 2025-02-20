package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.CheckEmailResponseDto
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun postLogin(
        email: String,
        password: String
    ): Response<Unit>

    suspend fun postCheckEmail(
        email: String
    ): BaseResponse<CheckEmailResponseDto>

    suspend fun postSignup(
        email: String,
        password: String,
        nickname: String
    ): Response<Unit>
}