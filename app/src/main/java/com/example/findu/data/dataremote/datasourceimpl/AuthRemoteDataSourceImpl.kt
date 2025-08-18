package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.AuthRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.request.CheckNicknameRequestDto
import com.example.findu.data.dataremote.model.request.GuestLoginRequestDto
import com.example.findu.data.dataremote.model.request.LoginRequestDto
import com.example.findu.data.dataremote.model.response.CheckNicknameResponseDto
import com.example.findu.data.dataremote.model.response.auth.GuestLoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.LoginResponseDto
import com.example.findu.data.dataremote.model.response.auth.UserInfoDto
import com.example.findu.data.dataremote.service.AuthService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {
    override suspend fun postLogin(loginRequestDto: LoginRequestDto): BaseResponse<LoginResponseDto> =
        authService.postLogin(loginRequestDto=loginRequestDto)

    override suspend fun postGuestLogin(guestLoginRequestDto: GuestLoginRequestDto): BaseResponse<GuestLoginResponseDto> =
        authService.postGuestLogin(guestLoginRequestDto=guestLoginRequestDto)

    override suspend fun postCheckNickname(nickname: String): BaseResponse<CheckNicknameResponseDto> =
        authService.postCheckNickname(CheckNicknameRequestDto(nickname))

    override suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long,
        deviceId: String
    ): BaseResponse<UserInfoDto> = authService.postSignup(
        profileImage = profileImageFile?.let {
            MultipartBody.Part.createFormData(
                "profileImage",
                it.name,
                it.asRequestBody("image/*".toMediaTypeOrNull())
            )
        },
        defaultImageName = defaultImageName?.toRequestBody("text/plain".toMediaTypeOrNull()),
        nickname = nickname.toRequestBody("text/plain".toMediaTypeOrNull()),
        kakaoId = kakaoId.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
        deviceId = deviceId.toRequestBody("text/plain".toMediaTypeOrNull())
    )
}