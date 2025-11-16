package com.kuit.findu.domain.model.my

data class MyReportHistoryData(
    val isLast: Boolean,
    val lastId: Long,
    val reports: List<Report>
) {
    data class Report(
        val date: String,
        val location: String,
        val reportId: Long,
        val tag: String,
        val thumbnailImageUrl: String,
        val title: String,
        val interest : Boolean,
    )
}

