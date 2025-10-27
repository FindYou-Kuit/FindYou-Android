package com.kuit.findu.data.mapper.todomain

import com.kuit.findu.data.dataremote.model.response.HomeResponseDto
import com.kuit.findu.data.dataremote.model.response.PeriodStatisticsDto
import com.kuit.findu.data.dataremote.model.response.ProtectingAnimal
import com.kuit.findu.data.dataremote.model.response.Statistics
import com.kuit.findu.data.dataremote.model.response.WitnessedOrMissingAnimal

import com.kuit.findu.domain.model.HomeData
import com.kuit.findu.domain.model.HomeStatistics
import com.kuit.findu.domain.model.PeriodStatistics
import com.kuit.findu.domain.model.ProtectAnimal
import com.kuit.findu.domain.model.ReportAnimal

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