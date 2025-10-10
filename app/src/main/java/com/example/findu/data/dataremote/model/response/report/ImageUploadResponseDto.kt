package com.example.findu.data.dataremote.model.response.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageUploadResponseDto(
    @SerialName("urls")
    val urls: List<String>
)
