package com.kuit.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenReissueRequestDto(
    @SerialName("refreshToken")
    val refreshToken: String,
)
