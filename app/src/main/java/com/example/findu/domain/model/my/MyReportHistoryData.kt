package com.example.findu.domain.model.my

data class MyReportHistoryData(
    val isLast: Boolean,
    val lastReportId: Int,
    val reports: List<Report>
) {
    data class Report(
        val date: String,
        val location: String,
        val reportId: Int,
        val tag: String,
        val thumbnailImageUrl: String,
        val title: String
    )
}

