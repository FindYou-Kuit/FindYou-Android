package com.kuit.findu.data.dataremote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PatchNicknameRequestDto(
    val newNickname: String
)