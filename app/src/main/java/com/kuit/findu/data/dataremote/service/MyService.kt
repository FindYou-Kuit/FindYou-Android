package com.kuit.findu.data.dataremote.service

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.base.NullableBaseResponse
import com.kuit.findu.data.dataremote.model.request.PatchNicknameRequestDto
import com.kuit.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Query

interface MyService {
    @GET("/api/v2/users/me/interest-animals")
    suspend fun getInterestAnimals(
        @Query("lastId") lastId: Long? = null
    ): BaseResponse<MyInterestResponseDto>

    @GET("/api/v2/users/me/reports")
    suspend fun getReportHistory(
        @Query("lastId") lastId: Long? = null,
    ): BaseResponse<MyReportHistoryResponseDto>

    @GET("/api/v2/users/me/viewed-animals")
    suspend fun getViewedAnimals(
        @Query("lastId") lastId: Long? = null,
    ): BaseResponse<MyViewedAnimalsResponseDto>

    @DELETE("/api/v2/users/me")
    suspend fun deleteUser(): NullableBaseResponse<Unit>

    @PATCH("/api/v2/users/me/nickname")
    suspend fun patchNickname(
        @Body newNickname: PatchNicknameRequestDto
    ): NullableBaseResponse<Unit>

    @GET("/api/v2/users/me")
    suspend fun getNickname(): BaseResponse<MyNickNameResponseDto>

    @Multipart
    @PATCH("/api/v2/users/me/profile-image")
    suspend fun patchProfileImage(
        @Part profileImageFile: MultipartBody.Part? = null,
        @Part("defaultProfileImageName") defaultProfileImageName: RequestBody? = null
    ): NullableBaseResponse<Unit>

}