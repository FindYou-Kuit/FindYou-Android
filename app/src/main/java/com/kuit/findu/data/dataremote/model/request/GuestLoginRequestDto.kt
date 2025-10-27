package com.kuit.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestLoginRequestDto(
    @SerialName("deviceId")
    val deviceId: String,
)