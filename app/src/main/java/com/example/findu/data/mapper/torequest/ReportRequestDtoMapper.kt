package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.model.request.WitnessReportRequestDto
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun MissingReportData.toRequestDto() =
    MissingReportRequestDto(
        imgUrls = imageUrls,
        species = "DOG", // 기본값, 추후 ViewModel에서 제공 예정
        breed = "품종 미상", // 기본값, 추후 ViewModel에서 제공 예정
        age = "정보 없음", // 기본값
        sex = sex.value,
        rfid = "", // 기본값
        furColor = furColors.joinToString("&") { it.color },
        missingDate = Instant.fromEpochMilliseconds(missingDate.toEpochMilliseconds())
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date.toString(),
        significant = description,
        location = location,
        landmark = "" // 기본값
    )

fun WitnessReportData.toRequestDto() =
    WitnessReportRequestDto(
        imgUrls = imageUrls,
        breed = "품종 미상", // 기본값, 추후 ViewModel에서 제공 예정
        species = "DOG", // 기본값, 추후 ViewModel에서 제공 예정
        furColor = furColors.joinToString("&") { it.color },
        location = location,
        landmark = "", // 기본값
        significant = description,
        foundDate = Instant.fromEpochMilliseconds(foundDate.toEpochMilliseconds())
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date.toString()
    )