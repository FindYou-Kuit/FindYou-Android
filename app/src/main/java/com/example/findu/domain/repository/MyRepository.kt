package com.example.findu.domain.repository

import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyProfileData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    suspend fun patchProfileImageFile(file: MultipartBody.Part): Result<Unit>

    suspend fun patchProfileImageDefault(defaultProfileImageName: RequestBody): Result<Unit>

}