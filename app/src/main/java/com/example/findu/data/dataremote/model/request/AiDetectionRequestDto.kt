package com.example.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiDetectionRequestDto(
    @SerialName("imageUrl")
    val imageUrl: String
)