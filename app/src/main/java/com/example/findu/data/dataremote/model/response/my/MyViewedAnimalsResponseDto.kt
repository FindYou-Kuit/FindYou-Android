package com.example.findu.data.dataremote.model.response.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyViewedAnimalsResponseDto(
    @SerialName("isLast")
    val isLast: Boolean,
    @SerialName("lastId")
    val lastId: Long,
    @SerialName("cards")
    val cards: List<Card>
) {
    @Serializable
    data class Card(
        @SerialName("reportId")
        val reportId: Long,
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

    )
}