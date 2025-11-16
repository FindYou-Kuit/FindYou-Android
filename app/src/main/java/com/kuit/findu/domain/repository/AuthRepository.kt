package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.GuestLoginData
import com.kuit.findu.domain.model.LoginData
import com.kuit.findu.domain.model.LoginInfo
import com.kuit.findu.domain.model.UserInfo
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
        kakaoId: Long,
        deviceId: String
    ): Result<UserInfo>
}