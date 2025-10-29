package com.example.findu.data.dataremote.model.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponseDto(
    @SerialName("statistics")
    val statistics: Statistics,
    @SerialName("protectingAnimals")
    val protectingAnimals: List<ProtectingAnimal>,
    @SerialName("witnessedOrMissingAnimals")
    val witnessedOrMissingAnimals: List<WitnessedOrMissingAnimal>
)

@Serializable
data class Statistics(
    @SerialName("recent7days")
    val recent7days: PeriodStatisticsDto,
    @SerialName("recent3months")
    val recent3months: PeriodStatisticsDto,
    @SerialName("recent1Year")
    val recent1Year: PeriodStatisticsDto
)

@Serializable
data class PeriodStatisticsDto(
    @SerialName("rescuedAnimalCount")
    val rescuedAnimalCount: Int,
    @SerialName("protectingAnimalCount")
    val protectingAnimalCount: Int,
    @SerialName("adoptedAnimalCount")
    val adoptedAnimalCount: Int,
    @SerialName("lostAnimalCount")
    val lostAnimalCount: Int
)

@Serializable
data class ProtectingAnimal(
    @SerialName("reportId")
    val reportId: Int,
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
data class WitnessedOrMissingAnimal(
    @SerialName("reportId")
    val reportId: Int,
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