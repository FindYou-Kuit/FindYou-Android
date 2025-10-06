package com.example.findu.data.mapper.todomain.home

import com.example.findu.data.dataremote.model.response.home.HomeResponseDto
import com.example.findu.data.dataremote.model.response.home.PeriodStatisticsDto
import com.example.findu.data.dataremote.model.response.home.ProtectingAnimal
import com.example.findu.data.dataremote.model.response.home.Statistics
import com.example.findu.data.dataremote.model.response.home.WitnessedOrMissingAnimal
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.HomeStatistics
import com.example.findu.domain.model.PeriodStatistics
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal

fun HomeResponseDto.toDomain() = HomeData(
    todayRescuedAnimalCount = statistics.recent7days.rescuedAnimalCount,
    todayReportAnimalCount = statistics.recent7days.lostAnimalCount,
    protectAnimalCards = protectingAnimals.map { it.toDomain() },
    reportAnimalCards = witnessedOrMissingAnimals.map { it.toDomain() },
    statistics = statistics.toDomain()
)

fun Statistics.toDomain() = HomeStatistics(
    recent7days = recent7days.toDomain(),
    recent3months = recent3months.toDomain(),
    recent1Year = recent1Year.toDomain()
)

fun PeriodStatisticsDto.toDomain() = PeriodStatistics(
    rescuedAnimalCount = rescuedAnimalCount,
    protectingAnimalCount = protectingAnimalCount,
    adoptedAnimalCount = adoptedAnimalCount,
    reportedAnimalCount = lostAnimalCount
)

fun ProtectingAnimal.toDomain() = ProtectAnimal(
    protectId = reportId,
    thumbnailImageUrl = thumbnailImageUrl,
    title = title,
    tag = tag,
    noticeStartDate = happenDate,
    careAddress = careAddress
)

fun WitnessedOrMissingAnimal.toDomain() = ReportAnimal(
    reportId = reportId,
    thumbnailImageUrl = thumbnailImageUrl,
    title = title,
    tag = tag,
    registerDate = happenDate,
    happenLocation = careAddress
)