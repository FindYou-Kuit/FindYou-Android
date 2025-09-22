package com.example.findu.data.dataremote.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameRequestDto(
    @SerialName("nickname")
    val nickname: String
)