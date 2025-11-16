package com.kuit.findu.data.dataremote.model.response.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyInterestResponseDto(
    @SerialName("cards")
    val interestAnimals: List<InterestAnimalDto>,
    @SerialName("isLast")
    val isLast: Boolean,
    @SerialName("lastId")
    val lastId: Long,
) {
    @Serializable
    data class InterestAnimalDto(
        @SerialName("reportId")
        val reportId: Long,
        @SerialName("thumbnailImageUrl")
        val thumbnailImageUrl: String,
        @SerialName("title")
        val title: String? = null,
        @SerialName("tag")
        val tag: String,
        @SerialName("date")
        val date: String,
        @SerialName("location")
        val address: String,


        )
}