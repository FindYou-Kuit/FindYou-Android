package com.example.findu.data.dataremote.service

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.home.HomeResponseDto
import com.example.findu.data.dataremote.model.response.information.CentersResponseDto
import com.example.findu.data.dataremote.model.response.information.DepartmentsResponseDto
import com.example.findu.data.dataremote.model.response.information.SidoListDto
import com.example.findu.data.dataremote.model.response.information.SigunguListDto
import com.example.findu.data.dataremote.model.response.information.VolunteersResponseDto
import com.example.findu.data.dataremote.util.ApiConstraints.API
import com.example.findu.data.dataremote.util.ApiConstraints.INFORMATION
import com.example.findu.data.dataremote.util.ApiConstraints.VERSION
import retrofit2.http.GET
import retrofit2.http.Query

interface InformationService {
    @GET("/$API/$VERSION/$INFORMATION/volunteer-works")
    suspend fun getVolunteers(
        @Query("lastId") lastId: Long? = Long.MAX_VALUE,
    ): BaseResponse<VolunteersResponseDto>

    @GET("/$API/$VERSION/$INFORMATION/protection-centers")
    suspend fun getCenters(
        @Query("lastId") lastId: Long? = null,
        @Query("district") sido: String? = null,
        @Query("lat") lat: Double? = null,
        @Query("long") long: Double? = null,
        @Query("size") size: Int? = null,
        ): BaseResponse<CentersResponseDto>

    @GET("/$API/$VERSION/$INFORMATION/departments")
    suspend fun getDepartments(
        @Query("district") district: String? = null,
        @Query("lastId") lastId: Long? = null,
    ): BaseResponse<DepartmentsResponseDto>

    @GET("/$API/$VERSION/sidos")
    suspend fun getSido(): BaseResponse<SidoListDto>

    @GET("/$API/$VERSION/sigungus")
    suspend fun getSigungu(
        @Query("sidoId") sidoId: Long): BaseResponse<SigunguListDto>
}