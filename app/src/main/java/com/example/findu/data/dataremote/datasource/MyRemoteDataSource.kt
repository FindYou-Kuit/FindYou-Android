package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto

interface MyRemoteDataSource {
    suspend fun getInterestAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): BaseResponse<MyInterestResponseDto>
}