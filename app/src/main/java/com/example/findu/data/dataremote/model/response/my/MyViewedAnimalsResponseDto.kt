package com.example.findu.data.dataremote.model.response.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyViewedAnimalsResponseDto(
    @SerialName("isLast")
    val isLast: Boolean,
    @SerialName("lastViewedProtectId")
    val lastViewedProtectId: Long,
    @SerialName("lastViewedReportId")
    val lastViewedReportId: Long,
    @SerialName("viewedAnimals")
    val viewedAnimals: List<ViewedAnimal>
) {
    @Serializable
    data class ViewedAnimal(
        @SerialName("cardId")
        val cardId: Long,
        @SerialName("date")
        val date: String,
        @SerialName("interest")
        val interest: Boolean,
        @SerialName("location")
        val location: String,
        @SerialName("tag")
        val tag: String,
        @SerialName("thumbnailImageUrl")
        val thumbnailImageUrl: String,
        @SerialName("title")
        val title: String
    )
}