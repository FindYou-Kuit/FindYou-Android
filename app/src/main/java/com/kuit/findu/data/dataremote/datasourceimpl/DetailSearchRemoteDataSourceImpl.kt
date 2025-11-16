package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.search.DetailMissingResponseDto
import com.kuit.findu.data.dataremote.model.response.search.DetailProtectResponseDto
import com.kuit.findu.data.dataremote.model.response.search.DetailWitnessResponseDto
import com.kuit.findu.data.dataremote.service.DetailSearchService
import javax.inject.Inject

class DetailSearchRemoteDataSourceImpl @Inject constructor(
    private val detailService: DetailSearchService
): DetailSearchRemoteDataSource {
    override suspend fun getDetailSearchProtect(reportId: Long): BaseResponse<DetailProtectResponseDto> =
        detailService.getDetailSearchProtect(reportId)

    override suspend fun getDetailSearchMissing(reportId: Long): BaseResponse<DetailMissingResponseDto> =
        detailService.getDetailSearchMissing(reportId)

    override suspend fun getDetailSearchWitness(reportId: Long): BaseResponse<DetailWitnessResponseDto> =
        detailService.getDetailSearchWitness(reportId)
}