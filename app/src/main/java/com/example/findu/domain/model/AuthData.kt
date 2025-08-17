package com.example.findu.domain.model

data class LoginInfo(
    val kakaoId: Long,
    val deviceId:String
)

data class LoginData(
    val isFirstLogin: Boolean,
    val userInfo: UserInfo?
)

data class GuestLoginData(
    val userId: Long,
    val accessToken: String
)

data class UserInfo(
    val userId: Long,
    val nickname: String,
    val accessToken: String
)