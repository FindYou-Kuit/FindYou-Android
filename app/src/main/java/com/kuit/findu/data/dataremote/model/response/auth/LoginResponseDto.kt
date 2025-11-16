package com.kuit.findu.data.dataremote.model.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("userInfo")
    val userInfo: UserInfoDto? = null,
    @SerialName("isFirstLogin")
    val isFirstLogin: Boolean
)

@Serializable
data class UserInfoDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("accessToken")
    val accessToken: String
)