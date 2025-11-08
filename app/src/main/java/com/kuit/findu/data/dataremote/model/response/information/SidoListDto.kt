package com.kuit.findu.data.dataremote.model.response.information

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SidoListDto(
    @SerialName("sidoList")
    val sidoList: List<SidoItemDto>
)

@Serializable
data class SidoItemDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)
