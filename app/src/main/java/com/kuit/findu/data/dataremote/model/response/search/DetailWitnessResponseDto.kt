package com.kuit.findu.data.dataremote.model.response.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailWitnessResponseDto(
    @SerialName("imageUrls") val imageUrls: List<String> = emptyList(),
    @SerialName("breed") val breed: String,
    @SerialName("tag") val tag: String,
    @SerialName("furColor") val furColor: String,
    @SerialName("significant") val significant: String,
    @SerialName("witnessLocation") val witnessLocation: String,
    @SerialName("witnessAddress") val witnessAddress: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("reporterInfo") val reporterInfo: String,
    @SerialName("witnessDate") val witnessDate: String,
    @SerialName("interest") val interest: Boolean
)