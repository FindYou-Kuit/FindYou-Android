package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.CheckNicknameRequestDto
import com.kuit.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.kuit.findu.data.dataremote.model.request.LoginRequestDto
import com.kuit.findu.data.dataremote.model.request.PostUserRequestDto
import com.kuit.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.kuit.findu.data.dataremote.model.response.auth.UserInfoDto
import com.kuit.findu.data.dataremote.service.AuthService
import java.io.File
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun postLogin(loginRequestDto: LoginRequestDto): NullableBaseResponse<LoginResponseDto> =
        authService.postLogin(loginRequestDto = loginRequestDto)

    override suspend fun postGuestLogin(guestLoginRequestDto: GuestLoginRequestDto): NullableBaseResponse<GuestLoginResponseDto> =
        authService.postGuestLogin(guestLoginRequestDto = guestLoginRequestDto)

    override suspend fun postCheckNickname(nickname: String): BaseResponse<CheckNicknameResponseDto> =
        authService.postCheckNickname(CheckNicknameRequestDto(nickname))

    override suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long,
        deviceId: String,
    ): NullableBaseResponse<UserInfoDto> = authService.postSignup(
        PostUserRequestDto(nickname, kakaoId, deviceId)
    )
}