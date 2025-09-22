package com.example.findu.data.dataremote.model.response.information

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CentersResponseDto(
    @SerialName("centers")
    val centers: List<CenterDto>,

    @SerialName("lastId")
    val lastId: Long,

    @SerialName("isLast")
    val isLast: Boolean
)

@Serializable
data class CenterDto(
    @SerialName("jurisdiction")
    val jurisdiction: List<String>,

    @SerialName("centerName")
    val centerName: String,

    @SerialName("phoneNumber")
    val phoneNumber: String,

    @SerialName("address")
    val address: String
)