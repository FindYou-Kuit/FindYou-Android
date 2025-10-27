package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.kuit.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MyRemoteDataSource {
    suspend fun getInterestAnimals(
        lastId: Long,
    ): BaseResponse<MyInterestResponseDto>

    suspend fun getReportHistory(
        lastId: Long
    ): BaseResponse<MyReportHistoryResponseDto>

    suspend fun getViewedAnimals(
        lastId: Long,
    ): BaseResponse<MyViewedAnimalsResponseDto>

    suspend fun deleteUser(): NullableBaseResponse<Unit>

    suspend fun patchNickname(request: PatchNicknameRequestDto): NullableBaseResponse<Unit>

    suspend fun getNickname(): BaseResponse<MyNickNameResponseDto>

    suspend fun patchProfileImageFile(file: MultipartBody.Part): NullableBaseResponse<Unit>

    suspend fun patchProfileImageDefault(defaultProfileImageName: RequestBody): NullableBaseResponse<Unit>
}