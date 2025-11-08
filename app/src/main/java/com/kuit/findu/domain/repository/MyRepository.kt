package com.kuit.findu.domain.repository

import com.kuit.findu.domain.model.my.MyInterestData
import com.kuit.findu.domain.model.my.MyProfileData
import com.kuit.findu.domain.model.my.MyProfileImageUpdate
import com.kuit.findu.domain.model.my.MyReportHistoryData
import com.kuit.findu.domain.model.my.MyViewedAnimalData

interface MyRepository {
    suspend fun getMyInterest(
        lastId : Long
    ): Result<MyInterestData>

    suspend fun getMyReportHistory(
        lastId: Long
    ): Result<MyReportHistoryData>

    suspend fun getMyViewedAnimals(
        lastId: Long,
    ): Result<MyViewedAnimalData>

    suspend fun deleteUser(): Result<Unit>

    suspend fun patchNickname(newNickname: String): Result<Unit>

    suspend fun getNickname(): Result<MyProfileData>

    suspend fun patchProfileImage(update: MyProfileImageUpdate): Result<Unit>

}