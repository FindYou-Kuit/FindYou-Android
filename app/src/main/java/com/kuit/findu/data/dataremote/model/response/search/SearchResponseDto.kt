package com.kuit.findu.data.dataremote.model.response.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("cards")
    val cards: List<SearchAnimalCard>,
    @SerialName("lastId")
    val lastId: Long?,
    @SerialName("isLast")
    val isLast: Boolean,
)

@Serializable
data class SearchAnimalCard(
    @SerialName("reportId")
    val reportId: Long,
    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String?,
    @SerialName("title")
    val title: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("date")
    val date: String,
    @SerialName("location")
    val location: String,
    @SerialName("interest")
    val interest: Boolean = false,
    @SerialName("createdAt")
    val createdAt: String,
)
