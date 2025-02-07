package com.example.findu.domain.model

data class HomeData(
    val todayRescuedAnimalCount: Int,
    val todayReportAnimalCount: Int,
    val protectAnimalCards: List<ProtectAnimal>,
    val reportAnimalCards: List<ReportAnimal>
)

data class ProtectAnimal(
    val protectId: Int,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val noticeStartDate: String,
    val careAddress: String
)

data class ReportAnimal(
    val reportId: Int,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val registerDate: String,
    val happenLocation: String
)
