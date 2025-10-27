package com.kuit.findu.presentation.model

data class MyReportHistoryRv(
    val reportId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String
)

data class MyViewedAnimalsRv(
    val reportId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String,
    var interest: Boolean,
)

data class MyInterestRv(
    val reportId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val address: String,
    var interest: Boolean,
)