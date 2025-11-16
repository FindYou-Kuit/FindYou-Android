package com.kuit.findu.data.dataremote.model.response.information

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SigunguListDto(
    @SerialName("sigunguList")
    val sigunguList: List<String>
)
