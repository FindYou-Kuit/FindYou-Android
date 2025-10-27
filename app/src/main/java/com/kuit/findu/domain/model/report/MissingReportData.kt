package com.kuit.findu.domain.model.report

import com.kuit.findu.domain.model.breed.SpeciesType

data class MissingReportData(
    val imageUrls: List<String>,
    val species: SpeciesType,
    val breed: String,
    val age: String,
    val sex: Gender,
    val rfid: String,
    val furColors: List<FurColorType>,
    val location: String,
    val landmark: String,
    val description: String,
    val missingDate: String,
)
