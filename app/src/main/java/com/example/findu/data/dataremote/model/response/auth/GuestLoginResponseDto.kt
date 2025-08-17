package com.example.findu.data.dataremote.model.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestLoginResponseDto(
    @SerialName("userId")
    val userId: Long,
    @SerialName("accessToken")
    val accessToken: String
)
