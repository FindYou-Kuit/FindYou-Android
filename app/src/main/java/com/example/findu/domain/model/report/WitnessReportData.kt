package com.example.findu.domain.model.report

data class WitnessReportData(
    val imageUrls: List<String>,
    val breed: String,
    val species: String,
    val furColor: String,
    val location: String,
    val landmark: String,
    val significant: String,
    val foundDate: String
)
