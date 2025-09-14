package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.model.request.WitnessReportRequestDto
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData

fun MissingReportData.toRequestDto() =
    MissingReportRequestDto(
        imgUrls = imageUrls,
        species = species,
        breed = breed,
        age = age,
        sex = sex,
        rfid = rfid,
        furColor = furColor,
        missingDate = missingDate,
        significant = significant,
        location = location,
        landmark = landmark
    )

fun WitnessReportData.toRequestDto() =
    WitnessReportRequestDto(
        imgUrls = imageUrls,
        breed = breed,
        species = species,
        furColor = furColor,
        location = location,
        landmark = landmark,
        significant = significant,
        foundDate = foundDate
    )