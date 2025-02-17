package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.domain.model.report.MissingReportData

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