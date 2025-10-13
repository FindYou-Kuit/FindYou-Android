package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.MyRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import com.example.findu.data.dataremote.service.MyService
import com.example.findu.data.dataremote.util.handleBaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyRemoteDataSourceImpl @Inject constructor(
    private val myService: MyService,
) : MyRemoteDataSource {
    override suspend fun getInterestAnimals(
        lastId: Long,
    ): BaseResponse<MyInterestResponseDto> =
        myService.getInterestAnimals(lastId)

    override suspend fun getReportHistory(lastId: Long): BaseResponse<MyReportHistoryResponseDto> =
        myService.getReportHistory(lastId)

    override suspend fun getViewedAnimals(
        lastId: Long,
    ): BaseResponse<MyViewedAnimalsResponseDto> =
        myService.getViewedAnimals(lastId)

    override suspend fun deleteUser(): NullableBaseResponse<Unit> =
        myService.deleteUser()

    override suspend fun patchNickname(request: PatchNicknameRequestDto): NullableBaseResponse<Unit> =
        myService.patchNickname(request)

    override suspend fun getNickname(): BaseResponse<MyNickNameResponseDto> =
        myService.getNickname()

    override suspend fun patchProfileImageFile(file: MultipartBody.Part): NullableBaseResponse<Unit> =
        myService.patchProfileImageFile(file)


    override suspend fun patchProfileImageDefault(defaultProfileImageName: RequestBody): NullableBaseResponse<Unit> =
        myService.patchProfileImageDefault(defaultProfileImageName)

}