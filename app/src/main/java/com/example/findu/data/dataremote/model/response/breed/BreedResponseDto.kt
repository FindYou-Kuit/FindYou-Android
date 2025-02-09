package com.example.findu.data.dataremote.model.response.breed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedResponseDto(
    @SerialName("breedId")
    val breedId: Int,
    @SerialName("breedName")
    val breedName: String,
    @SerialName("species")
    val species : String
)
