package com.example.findu.presentation.model

data class MyReportHistoryRv(
    val reportId: Int,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String
)

data class MyRecentHistoryRv(
    val cardId: Int,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: String,
    val date: String,
    val location: String,
    var interest: Boolean,
)