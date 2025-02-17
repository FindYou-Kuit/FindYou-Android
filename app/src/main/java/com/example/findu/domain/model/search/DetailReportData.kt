package com.example.findu.domain.model.search

import java.io.Serializable

data class DetailReportData(
    val imageUrls: List<String>,
    val breed: String,
    val tag: SearchStatus,
    val sex: String,
    val furColor: String,
    val userName: String,
    val writeDate: String,
    val eventDate: String,
    val eventLocation: String,
    val foundLocation: String,
    val features: List<String>,
    val additionalDescription: String,
    var interest: Boolean
) : Serializable

