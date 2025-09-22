package com.example.findu.domain.model.search

import java.io.Serializable

data class DetailProtectData(
    val imageUrls: List<String>,
    val breed: String,
    val tag: String,
    val age: String,
    val weight: String,
    val furColor: String,
    val sex: String,
    val neutering: String,
    val significant: String,
    val careName: String,
    val careAddr: String,
    val latitude: Double,
    val longitude: Double,
    val careTel: String,
    val foundDate: String,
    val foundLocation: String,
    val noticeDuration: String,
    val noticeNumber: String,
    val authority: String,
    val interest: Boolean
) : Serializable

