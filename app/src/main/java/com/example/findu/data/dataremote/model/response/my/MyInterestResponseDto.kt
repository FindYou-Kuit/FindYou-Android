package com.example.findu.data.dataremote.model.response.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyInterestResponseDto(
    @SerialName("interestAnimals")
    val interestAnimals: List<InterestAnimal>,
    @SerialName("isLast")
    val isLast: Boolean,
    @SerialName("lastInterestProtectId")
    val lastInterestProtectId: Int,
    @SerialName("lastInterestReportId")
    val lastInterestReportId: Int
) {
    @Serializable
    data class InterestAnimal(
        @SerialName("animalId")
        val animalId: Int,
        @SerialName("date")
        val date: String,
        @SerialName("interest")
        val interest: Boolean,
        @SerialName("interestId")
        val interestId: Int,
        @SerialName("isProtectingAnimal")
        val isProtectingAnimal: Boolean,
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