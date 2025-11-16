package com.kuit.findu.domain.model.report

import com.kuit.findu.domain.model.breed.SpeciesType

data class WitnessReportData(
    val imageUrls: List<String>,
    val species: SpeciesType,
    val breed: String,
    val furColors: List<FurColorType>,
    val location: String,
    val landmark: String,
    val description: String,
    val foundDate: String
)
