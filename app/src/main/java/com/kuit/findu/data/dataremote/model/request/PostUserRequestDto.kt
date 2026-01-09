package com.kuit.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostUserRequestDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("kakaoId")
    val kakaoId: Long,
    @SerialName("deviceId")
    val deviceId: String,
)
