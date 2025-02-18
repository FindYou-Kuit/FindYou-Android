package com.example.findu.domain.repository

import com.example.findu.domain.model.my.MyInterestData

interface MyRepository {
    suspend fun getMyInterest(
        lastReportId: Long,
        lastProtectId: Long,
    ): Result<MyInterestData>
}