package com.example.findu.domain.repository

import com.example.findu.domain.model.GuestLoginData
import com.example.findu.domain.model.LoginData
import com.example.findu.domain.model.LoginInfo

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
        email: String,
        password: String,
        nickname: String
    ): Result<String>
}