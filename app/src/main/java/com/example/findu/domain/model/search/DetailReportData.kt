package com.example.findu.domain.model.search

import java.io.Serializable

data class DetailReportData(
    val imageUrls: List<String>,
    val breed: String,
    val tag: SearchStatus,
    val sex: String?,
    val age : String?,
    val rfid : String?,
    val furColor: String,
    val userName: String,
    val userPhone : String,
    val surroundPlace : String,
    val writeDate: String,
    val eventDate: String,
    val eventLocation: String,
    val foundLocation: String,
    val features: List<String>,
    val specialNote: String,
    var interest: Boolean
) : Serializable

