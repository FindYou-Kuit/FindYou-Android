package com.example.findu.domain.repository

import com.example.findu.data.dataremote.model.response.auth.UserInfoDto
import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.LoginInfo
import com.example.findu.domain.model.UserInfo
import java.io.File

interface AuthRepository {
    suspend fun postLogin(
        loginInfo: LoginInfo
    ): Result<LoginData>

    suspend fun postGuestLogin(
        deviceId: String
    ): Result<GuestLoginData>

    suspend fun postCheckNickname(
        nickname: String
    ): Result<Boolean>

    suspend fun postSignup(
        profileImageFile: File?,
        defaultImageName: String?,
        nickname: String,
        kakaoId: Long
    ): Result<UserInfo>
}