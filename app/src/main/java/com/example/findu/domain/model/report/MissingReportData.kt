package com.example.findu.domain.model.report

import kotlinx.datetime.Instant

data class MissingReportData(
    val imageKeys: List<Int>,
    val breedId: Int,
    val sex: SexType,
    val furColors: List<FurColorType>,
    val location: String,
    val featureIds: List<Int>,
    val description: String,
    val missingDate: Instant
)
