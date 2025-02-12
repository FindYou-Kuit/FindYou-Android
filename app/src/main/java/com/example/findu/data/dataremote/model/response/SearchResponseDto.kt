package com.example.findu.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("cardId")
    val cardId: Long,
    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("date")
    val date: String,
    @SerialName("location")
    val location: String,
    @SerialName("interest")
    val interest: Boolean,
    @SerialName("lastProtectId")
    val lastProtectId: Long,
    @SerialName("lastReportId")
    val lastReportId: Long,
    @SerialName("isLast")
    val isLast: Boolean,
)
