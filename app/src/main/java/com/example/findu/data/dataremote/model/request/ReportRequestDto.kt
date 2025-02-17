package com.example.findu.data.dataremote.model.request

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MissingReportRequestDto(
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("breed")
    val breed: Int,
    @SerialName("sex")
    val sex: String,
    @SerialName("furColor")
    val furColor: List<String>,
    @SerialName("location")
    val location: String,
    @SerialName("features")
    val features: List<Int>,
    @SerialName("description")
    val description: String,
    @SerialName("missingDate")
    val missingDate: Instant
)

@Serializable
data class WitnessReportRequestDto(
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("breed")
    val breed: Int,
    @SerialName("furColor")
    val furColor: List<String>,
    @SerialName("location")
    val location: String,
    @SerialName("features")
    val features: List<Int>,
    @SerialName("description")
    val description: String,
    @SerialName("missingDate")
    val missingDate: Instant
)