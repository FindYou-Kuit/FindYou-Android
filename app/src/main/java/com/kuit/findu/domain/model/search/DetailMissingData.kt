package com.kuit.findu.domain.model.search

import java.io.Serializable

data class DetailMissingData(
    val imageUrls: List<String>,
    val breed: String,
    val tag: String,
    val age: String,
    val sex: String,
    val missingDate: String,
    val rfid: String,
    val significant: String,
    val missingLocation: String,
    val missingAddress: String,
    val latitude: Double,
    val longitude: Double,
    val reporterName: String,
    val reporterTel: String,
    val interest: Boolean
) : Serializable