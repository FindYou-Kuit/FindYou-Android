package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.MyRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.kuit.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import com.kuit.findu.data.dataremote.service.MyService
import com.kuit.findu.data.mapper.torequest.toImageMultipart
import com.kuit.findu.data.mapper.torequest.toPlainTextRequestBody
import java.io.File
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

    override suspend fun patchProfileImage(
        profileImageFile: File?,
        defaultImageName: String?,
    ): NullableBaseResponse<Unit> =
        myService.patchProfileImage(
            profileImageFile = profileImageFile?.toImageMultipart("profileImageFile"),
            defaultProfileImageName = defaultImageName?.toPlainTextRequestBody()
        )

}