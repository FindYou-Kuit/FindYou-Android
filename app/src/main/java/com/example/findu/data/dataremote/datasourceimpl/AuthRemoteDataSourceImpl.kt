package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.CheckNicknameRequestDto
import com.example.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.example.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.UserInfoDto
import com.example.findu.data.dataremote.service.AuthService
import com.example.findu.data.mapper.torequest.toImageMultipart
import com.example.findu.data.mapper.torequest.toPlainTextRequestBody
import java.io.File
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun postLogin(loginRequestDto: LoginRequestDto): NullableBaseResponse<LoginResponseDto> =
        authService.postLogin(loginRequestDto=loginRequestDto)

    override suspend fun postGuestLogin(guestLoginRequestDto: GuestLoginRequestDto): NullableBaseResponse<GuestLoginResponseDto> =
        authService.postGuestLogin(guestLoginRequestDto=guestLoginRequestDto)

    override suspend fun postCheckNickname(nickname: String): BaseResponse<CheckNicknameResponseDto> =
        authService.postCheckNickname(CheckNicknameRequestDto(nickname))

    override suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long,
        deviceId: String
    ): NullableBaseResponse<UserInfoDto> = authService.postSignup(
        profileImage = profileImageFile?.toImageMultipart("profileImage"),
        defaultImageName = defaultImageName?.toPlainTextRequestBody(),
        nickname = nickname.toPlainTextRequestBody(),
        kakaoId = kakaoId.toString().toPlainTextRequestBody(),
        deviceId = deviceId.toPlainTextRequestBody()
    )
}