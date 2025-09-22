package com.example.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("kakaoId")
    val kakaoId: Long,
    @SerialName("deviceId")
    val deviceId: String,
)