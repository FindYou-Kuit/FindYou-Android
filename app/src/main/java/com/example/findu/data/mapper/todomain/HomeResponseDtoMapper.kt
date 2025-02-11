package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.HomeResponseDto
import com.example.findu.data.dataremote.model.response.ProtectAnimalCard
import com.example.findu.data.dataremote.model.response.ReportAnimalCard
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal

fun HomeResponseDto.toDomain() = HomeData(
    todayRescuedAnimalCount = todayRescuedAnimalCount,
    todayReportAnimalCount = todayRescuedAnimalCount,
    protectAnimalCards = protectAnimalCards.map { it.toDomain() },
    reportAnimalCards = reportAnimalCards.map { it.toDomain() }
)

fun ProtectAnimalCard.toDomain() = ProtectAnimal(
    protectId = protectId,
    thumbnailImageUrl = thumbnailImageUrl,
    title = title,
    tag = tag,
    noticeStartDate = noticeStartDate,
    careAddress = careAddress
)

fun ReportAnimalCard.toDomain() = ReportAnimal(
    reportId = reportId,
    thumbnailImageUrl = thumbnailImageUrl,
    title = title,
    tag = tag,
    registerDate = registerDate,
    happenLocation = happenLocation
)