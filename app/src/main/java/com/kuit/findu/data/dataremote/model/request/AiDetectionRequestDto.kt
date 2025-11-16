package com.kuit.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiDetectionRequestDto(
    @SerialName("base64Image")
    val base64Image: String
)