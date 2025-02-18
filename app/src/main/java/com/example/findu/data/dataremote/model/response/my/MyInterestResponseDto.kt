package com.example.findu.data.dataremote.model.response.my


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyInterestResponseDto(
    @SerialName("interestAnimals")
    val interestAnimals: List<InterestAnimalDto>,
    @SerialName("isLast")
    val isLast: Boolean,
    @SerialName("lastInterestProtectId")
    val lastInterestProtectId: Long,
    @SerialName("lastInterestReportId")
    val lastInterestReportId: Long
) {
    @Serializable
    data class InterestAnimalDto(
        @SerialName("animalId")
        val animalId: Long,
        @SerialName("date")
        val date: String,
        @SerialName("interest")
        val interest: Boolean,
        @SerialName("interestId")
        val interestId: Long,
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