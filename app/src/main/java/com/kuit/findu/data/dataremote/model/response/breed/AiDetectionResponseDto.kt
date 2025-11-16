package com.kuit.findu.data.dataremote.model.response.breed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiDetectionResponseDto(
    @SerialName("species")
    val species: String,
    @SerialName("breed")
    val breed: String,
    @SerialName("furColors")
    val furColors: List<String>
)