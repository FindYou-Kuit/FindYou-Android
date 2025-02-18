package com.example.findu.domain.repository

import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData

interface MyRepository {
    suspend fun getMyInterest(
        lastReportId: Long,
        lastProtectId: Long,
    ): Result<MyInterestData>

    suspend fun getMyReportHistory(
        lastReportId: Long
    ): Result<MyReportHistoryData>
}