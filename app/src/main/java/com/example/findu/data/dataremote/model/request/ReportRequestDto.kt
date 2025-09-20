package com.example.findu.data.dataremote.model.request

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MissingReportRequestDto(
    @SerialName("imgUrls")
    val imgUrls: List<String>,
    @SerialName("species")
    val species: String,
    @SerialName("breed")
    val breed: String,
    @SerialName("age")
    val age: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("rfid")
    val rfid: String,
    @SerialName("furColor")
    val furColor: String,
    @SerialName("missingDate")
    val missingDate: String,
    @SerialName("significant")
    val significant: String,
    @SerialName("location")
    val location: String,
    @SerialName("landmark")
    val landmark: String
)

@Serializable
data class WitnessReportRequestDto(
    @SerialName("imgUrls")
    val imgUrls: List<String>,
    @SerialName("breed")
    val breed: String,
    @SerialName("species")
    val species: String,
    @SerialName("furColor")
    val furColor: String,
    @SerialName("location")
    val location: String,
    @SerialName("landmark")
    val landmark: String,
    @SerialName("significant")
    val significant: String,
    @SerialName("foundDate")
    val foundDate: String
)