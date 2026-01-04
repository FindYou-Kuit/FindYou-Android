package com.kuit.findu.data.dataremote.model.response.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailProtectResponseDto(
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("breed")
    val breed: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("age")
    val age: String,
    @SerialName("weight")
    val weight: String,
    @SerialName("furColor")
    val furColor: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("neutering")
    val neutering: String,
    @SerialName("significant")
    val significant: String,
    @SerialName("careName")
    val careName: String,
    @SerialName("careAddr")
    val careAddr: String,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("careTel")
    val careTel: String,
    @SerialName("foundDate")
    val foundDate: String,
    @SerialName("foundLocation")
    val foundLocation: String,
    @SerialName("noticeDuration")
    val noticeDuration: String,
    @SerialName("noticeNumber")
    val noticeNumber: String,
    @SerialName("authority")
    val authority: String,
    @SerialName("interest")
    val interest: Boolean
)