package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.service.MyService
import javax.inject.Inject

class MyRemoteDataSourceImpl @Inject constructor(
    private val myService: MyService
) : MyRemoteDataSource {
    override suspend fun getInterestAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): BaseResponse<MyInterestResponseDto> =
        myService.getInterestAnimals(lastReportId, lastProtectId)

    override suspend fun getReportHistory(lastReportId: Long): BaseResponse<MyReportHistoryResponseDto> =
        myService.getReportHistory(lastReportId)
}