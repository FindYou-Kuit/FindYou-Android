package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.model.request.WitnessReportRequestDto
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData

fun MissingReportData.toRequestDto() =
    MissingReportRequestDto(
        imageUrls = imageUrls,
        breed = breedId,
        sex = sex.value,
        furColor = furColors.map { it.color },
        location = location,
        features = featureIds,
        description = description,
        missingDate = missingDate
    )

fun WitnessReportData.toRequestDto() =
    WitnessReportRequestDto(
        imageUrls = imageUrls,
        breed = breedId,
        furColor = furColors.map { it.color },
        location = location,
        features = featureIds,
        description = description,
        foundDate = foundDate
    )