package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

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

    suspend fun patchProfileImage(
        profileImageFile: File?,
        defaultImageName: String?
    ): NullableBaseResponse<Unit>}