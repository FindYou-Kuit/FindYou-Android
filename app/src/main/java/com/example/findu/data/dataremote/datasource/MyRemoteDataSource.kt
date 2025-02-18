package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto

interface MyRemoteDataSource {
    suspend fun getInterestAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): BaseResponse<MyInterestResponseDto>

    suspend fun getReportHistory(
        lastReportId: Long
    ): BaseResponse<MyReportHistoryResponseDto>
}