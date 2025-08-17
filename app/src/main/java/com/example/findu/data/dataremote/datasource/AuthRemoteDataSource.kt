package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.example.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.UserInfoDto
import retrofit2.Response
import java.io.File

interface AuthRemoteDataSource {
    suspend fun postLogin(
        loginRequestDto: LoginRequestDto
    ): BaseResponse<LoginResponseDto>

    suspend fun postGuestLogin(
        guestLoginRequestDto: GuestLoginRequestDto
    ): BaseResponse<GuestLoginResponseDto>

    suspend fun postCheckNickname(
        nickname: String
    ): BaseResponse<CheckNicknameResponseDto>

    suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long
    ): BaseResponse<UserInfoDto>
}