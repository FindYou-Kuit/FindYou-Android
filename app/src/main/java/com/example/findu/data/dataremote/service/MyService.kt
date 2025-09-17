package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.base.NullableBaseResponse
import com.example.findu.data.dataremote.model.response.my.MyInterestResponseDto
import com.example.findu.data.dataremote.model.response.my.MyNickNameResponseDto
import com.example.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import com.example.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface MyService {
    @GET("/api/v2/users/me/interest-animals")
    suspend fun getInterestAnimals(
        @Query("lastId") lastId: Long
    ): BaseResponse<MyInterestResponseDto>

    @GET("/api/v2/users/me/reports")
    suspend fun getReportHistory(
        @Query("lastId") lastId: Long,
    ): BaseResponse<MyReportHistoryResponseDto>

    @GET("/api/v2/users/me/viewed-animals")
    suspend fun getViewedAnimals(
        @Query("lastId") lastId: Long,
    ): BaseResponse<MyViewedAnimalsResponseDto>

    @DELETE("/api/v1/users")
    suspend fun deleteUser(): NullableBaseResponse<Unit>

    @PATCH("/api/v1/users/nickname")
    suspend fun patchNickname(
        @Body newNickname: String
    ): NullableBaseResponse<Unit>

    @GET("/api/v2/users/me")
    suspend fun getNickname(): BaseResponse<MyNickNameResponseDto>
}