package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.kuit.findu.data.dataremote.model.request.LoginRequestDto
import com.kuit.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.UserInfoDto
import java.io.File

interface AuthRemoteDataSource {
    suspend fun postLogin(
        loginRequestDto: LoginRequestDto
    ): NullableBaseResponse<LoginResponseDto>

    suspend fun postGuestLogin(
        guestLoginRequestDto: GuestLoginRequestDto
    ): NullableBaseResponse<GuestLoginResponseDto>

    suspend fun postCheckNickname(
        nickname: String
    ): BaseResponse<CheckNicknameResponseDto>

    suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long,
        deviceId: String
    ): NullableBaseResponse<UserInfoDto>
}