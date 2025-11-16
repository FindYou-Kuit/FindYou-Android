package com.kuit.findu.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameResponseDto(
    @SerialName("isDuplicate")
    val isDuplicate: Boolean
)