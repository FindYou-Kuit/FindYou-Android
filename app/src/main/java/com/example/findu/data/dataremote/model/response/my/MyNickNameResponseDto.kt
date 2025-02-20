package com.example.findu.data.dataremote.model.response.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyNickNameResponseDto(
    @SerialName("nickname")
    val nickname: String
)