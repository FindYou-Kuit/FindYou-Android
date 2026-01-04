package com.kuit.findu.data.dataremote.model.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestLoginResponseDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)
