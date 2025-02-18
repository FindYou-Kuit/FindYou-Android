package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
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

    override suspend fun getViewedAnimals(
        lastReportId: Long,
        lastProtectId: Long
    ): BaseResponse<MyViewedAnimalsResponseDto> =
        myService.getViewedAnimals(lastReportId, lastProtectId)

    override suspend fun deleteUser(): NullableBaseResponse<Unit> =
        myService.deleteUser()

    override suspend fun patchNickname(newNickname: String): NullableBaseResponse<Unit> =
        myService.patchNickname(newNickname)

    override suspend fun getNickname(): BaseResponse<MyNickNameResponseDto> =
        myService.getNickname()
}