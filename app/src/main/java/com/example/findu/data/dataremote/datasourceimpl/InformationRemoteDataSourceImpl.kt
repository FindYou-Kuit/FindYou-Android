package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.InformationRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.information.CentersResponseDto
import com.example.findu.data.dataremote.model.response.information.DepartmentsResponseDto
import com.example.findu.data.dataremote.model.response.information.SidoListDto
import com.example.findu.data.dataremote.model.response.information.SigunguListDto
import com.example.findu.data.dataremote.model.response.information.VolunteersResponseDto
import com.example.findu.data.dataremote.service.InformationService
import javax.inject.Inject

class InformationRemoteDataSourceImpl @Inject constructor(
    private val informationService: InformationService
) : InformationRemoteDataSource {

    override suspend fun getVolunteers(
        lastId: Long?
    ): BaseResponse<VolunteersResponseDto> = informationService.getVolunteers(lastId)

    override suspend fun getCenters(
        lastId: Long?,
        sido: String?,
        sigungu: String?,
        lat: Double?,
        long: Double?
    ): BaseResponse<CentersResponseDto> = informationService.getCenters(lastId, sido, sigungu, lat, long)


    override suspend fun getDepartments(
        district: String?,
        lastId: Long?
    ): BaseResponse<DepartmentsResponseDto> = informationService.getDepartments(district, lastId)

    override suspend fun getSido(): BaseResponse<SidoListDto> = informationService.getSido()

    override suspend fun getSigungu(sidoId:Long): BaseResponse<SigunguListDto> = informationService.getSigungu(sidoId = sidoId)

}