package com.example.findu.data.mapper.torequest

import com.example.findu.data.dataremote.model.request.MissingReportRequestDto
import com.example.findu.data.dataremote.model.request.WitnessReportRequestDto
import com.example.findu.domain.model.report.MissingReportData
import com.example.findu.domain.model.report.WitnessReportData

fun MissingReportData.toRequestDto() =
    MissingReportRequestDto(
        imgUrls = this.imageUrls,
        species = this.species.displayName,
        breed = this.breed,
        age = this.age,
        sex = this.sex.displayName,
        rfid = this.rfid,
        furColor = this.furColors.joinToString("&") { it.color },
        missingDate = this.missingDate,
        significant = this.description,
        location = this.location,
        landmark = this.landmark
    )

fun WitnessReportData.toRequestDto() =
    WitnessReportRequestDto(
        imgUrls = this.imageUrls,
        breed = this.breed,
        species = this.species.displayName,
        furColor = this.furColors.joinToString("&") { it.color },
        location = this.location,
        landmark = this.landmark,
        significant = this.description,
        foundDate = this.foundDate
    )