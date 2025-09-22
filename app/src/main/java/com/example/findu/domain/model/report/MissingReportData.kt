package com.example.findu.domain.model.report

import kotlinx.datetime.Instant

data class MissingReportData(
    val imageUrls: List<String>,
    val breedId: Int,
    val sex: Gender,
    val furColors: List<FurColorType>,
    val location: String,
    val featureIds: List<Int>,
    val description: String,
    val missingDate: Instant
)
