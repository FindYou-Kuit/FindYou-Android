package com.example.findu.domain.repository

import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData

interface MyRepository {
    suspend fun getMyInterest(
        lastReportId: Long,
        lastProtectId: Long,
    ): Result<MyInterestData>

    suspend fun getMyReportHistory(
        lastReportId: Long
    ): Result<MyReportHistoryData>

    suspend fun getMyViewedAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): Result<MyViewedAnimalData>

    suspend fun deleteUser(): Result<Unit>

    suspend fun patchNickname(newNickname: String): Result<Unit>
}