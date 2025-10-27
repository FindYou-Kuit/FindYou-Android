package com.kuit.findu.domain.model

data class HomeData(
    val todayRescuedAnimalCount: Int,
    val todayReportAnimalCount: Int,
    val protectAnimalCards: List<ProtectAnimal>,
    val reportAnimalCards: List<ReportAnimal>,
    val statistics: HomeStatistics
)

data class HomeStatistics(
    val recent7days: PeriodStatistics,
    val recent3months: PeriodStatistics,
    val recent1Year: PeriodStatistics
)

data class PeriodStatistics(
    val rescuedAnimalCount: Int,
    val protectingAnimalCount: Int,
    val adoptedAnimalCount: Int,
    val reportedAnimalCount: Int
) {
    fun getAllStatistics(): List<Pair<String, Int>> = listOf(
        StatisticsType.RESCUED.label to rescuedAnimalCount,
        StatisticsType.PROTECTING.label to protectingAnimalCount,
        StatisticsType.ADOPTED.label to adoptedAnimalCount,
        StatisticsType.REPORTED.label to reportedAnimalCount
    )
}

enum class StatisticsType(val label: String) {
    RESCUED("구조"),
    PROTECTING("보호중"),
    ADOPTED("입양"),
    REPORTED("신고")
}

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