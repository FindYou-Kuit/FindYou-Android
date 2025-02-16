package com.example.findu.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailSearchResponseDto(
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("breed")
    val breed: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("age")
    val age: String,
    @SerialName("weight")
    val weight: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("happenDate")
    val happenDate: String,
    @SerialName("furColor")
    val furColor: String,
    @SerialName("neutering")
    val neutering: String,
    @SerialName("significant")
    val significant: String,
    @SerialName("noticeNumber")
    val noticeNumber: String,
    @SerialName("noticeDuration")
    val noticeDuration: String,
    @SerialName("foundLocation")
    val foundLocation: String,
    @SerialName("careName")
    val careName: String,
    @SerialName("careAddr")
    val careAddr: String,
    @SerialName("careTel")
    val careTel: String,
    @SerialName("authority")
    val authority: String,
    @SerialName("authorityPhoneNumber")
    val authorityPhoneNumber: String,
    @SerialName("interest")
    val interest: Boolean
)