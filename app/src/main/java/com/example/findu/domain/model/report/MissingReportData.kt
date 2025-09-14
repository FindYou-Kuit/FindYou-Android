package com.example.findu.domain.model.report

data class MissingReportData(
    val imageUrls: List<String>,
    val species: String,
    val breed: String,
    val age: String,
    val sex: String,
    val rfid: String,
    val furColor: String,
    val missingDate: String,
    val significant: String,
    val location: String,
    val landmark: String
)
