package com.kuit.findu.domain.model.search

import java.io.Serializable

data class DetailWitnessData(
    val imageUrls: List<String>,
    val breed: String,
    val tag: String,
    val furColor: String,
    val significant: String,
    val witnessLocation: String,
    val witnessAddress: String,
    val latitude: Double,
    val longitude: Double,
    val reporterInfo: String,
    val witnessDate: String,
    val interest: Boolean
) : Serializable