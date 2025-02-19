package com.example.findu.data.dataremote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    @SerialName("yesterdayRescuedAnimalCount")
    val yesterdayRescuedAnimalCount: Int,
    @SerialName("yesterdayReportedAnimalCount")
    val yesterdayReportedAnimalCount: Int,
    @SerialName("protectAnimalCards")
    val protectAnimalCards: List<ProtectAnimalCard>,
    @SerialName("reportAnimalCards")
    val reportAnimalCards: List<ReportAnimalCard>
)

@Serializable
data class ProtectAnimalCard(
    @SerialName("protectId")
    val protectId: Int,
    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("happenDate")
    val happenDate: String,
    @SerialName("careAddress")
    val careAddress: String
)

@Serializable
data class ReportAnimalCard(
    @SerialName("reportId")
    val reportId: Int,
    @SerialName("thumbnailImageUrl")
    val thumbnailImageUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("tag")
    val tag: String,
    @SerialName("registerDate")
    val registerDate: String,
    @SerialName("happenLocation")
    val happenLocation: String
)