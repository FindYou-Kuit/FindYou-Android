package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
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

    suspend fun patchNickname(newNickname: String): NullableBaseResponse<Unit>

    suspend fun getNickname(): BaseResponse<MyNickNameResponseDto>

    suspend fun patchProfileImageFile(file: MultipartBody.Part): NullableBaseResponse<Unit>

    suspend fun patchProfileImageDefault(defaultProfileImageName: RequestBody): NullableBaseResponse<Unit>
}