package com.kuit.findu.data.dataremote.model.response.breed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedResponseDto(
    @SerialName("dogBreedList")
    val dogBreedList: List<String>,
    @SerialName("catBreedList")
    val catBreedList: List<String>,
    @SerialName("etcBreedList")
    val etcBreedList: List<String>
)

@Serializable
data class BreedValidationResponseDto(
    @SerialName("breedId")
    val breedId: Int,
    @SerialName("isExist")
    val isExist: Boolean
)
