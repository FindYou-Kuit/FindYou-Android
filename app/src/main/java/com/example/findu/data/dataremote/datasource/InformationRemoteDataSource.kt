package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.information.VolunteersResponseDto
import com.example.findu.data.dataremote.model.response.information.CentersResponseDto
import com.example.findu.data.dataremote.model.response.information.DepartmentsResponseDto
import com.example.findu.data.dataremote.model.response.information.SidoListDto
import com.example.findu.data.dataremote.model.response.information.SigunguListDto

interface InformationRemoteDataSource {
    suspend fun getVolunteers(
        lastId: Long? = Long.MAX_VALUE,
    ): BaseResponse<VolunteersResponseDto>

    suspend fun getCenters(
        lastId: Long? = null,
        district: String? = null,
        lat: Double? = null,
        long: Double? = null,
        size: Int? = null
    ): BaseResponse<CentersResponseDto>

    suspend fun getDepartments(
        district: String? = null,
        lastId: Long? = null,
    ): BaseResponse<DepartmentsResponseDto>

    suspend fun getSido(): BaseResponse<SidoListDto>

    suspend fun getSigungu(sidoId: Long): BaseResponse<SigunguListDto>
}