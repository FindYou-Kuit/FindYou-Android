package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto

interface MyRemoteDataSource {
    suspend fun getInterestAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): BaseResponse<MyInterestResponseDto>

    suspend fun getReportHistory(
        lastReportId: Long
    ): BaseResponse<MyReportHistoryResponseDto>

    suspend fun getViewedAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): BaseResponse<MyViewedAnimalsResponseDto>

    suspend fun deleteUser(): NullableBaseResponse<Unit>

    suspend fun patchNickname(newNickname: String): NullableBaseResponse<Unit>
}