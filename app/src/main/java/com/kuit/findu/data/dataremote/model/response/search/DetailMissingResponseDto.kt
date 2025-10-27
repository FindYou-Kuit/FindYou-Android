package com.kuit.findu.data.dataremote.model.response.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DetailMissingResponseDto(
    @SerialName("imageUrls") val imageUrls: List<String> = emptyList(),
    @SerialName("breed") val breed: String,
    @SerialName("tag") val tag: String,
    @SerialName("age") val age: String,
    @SerialName("sex") val sex: String,
    @SerialName("missingDate") val missingDate: String,
    @SerialName("rfid") val rfid: String,
    @SerialName("significant") val significant: String,
    @SerialName("missingLocation") val missingLocation: String,
    @SerialName("missingAddress") val missingAddress: String,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("reporterName") val reporterName: String,
    @SerialName("reporterTel") val reporterTel: String,
    @SerialName("interest") val interest: Boolean
)