package com.example.findu.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailReportResponseDto(
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("tag")
    val tag: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("breed")
    val breed: String,
    @SerialName("furColor")
    val furColor: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("writeDate")
    val writeDate: String,
    @SerialName("eventDate")
    val eventDate: String,
    @SerialName("eventLocation")
    val eventLocation: String? = null,
    @SerialName("foundLocation")
    val foundLocation: String? = null,
    @SerialName("features")
    val features: List<String>,
    @SerialName("additionalDescription")
    val additionalDescription: String,
    @SerialName("interest")
    val interest: Boolean
){
    val location: String
        get() = foundLocation ?: eventLocation ?: "위치 정보 없음"
}