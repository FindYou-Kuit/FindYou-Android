package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.search.DetailMissingResponseDto
import com.kuit.findu.data.dataremote.model.response.search.DetailProtectResponseDto
import com.kuit.findu.data.dataremote.model.response.search.DetailWitnessResponseDto

interface DetailSearchRemoteDataSource {
    suspend fun getDetailSearchProtect(reportId: Long): BaseResponse<DetailProtectResponseDto>
    suspend fun getDetailSearchMissing(reportId: Long): BaseResponse<DetailMissingResponseDto>
    suspend fun getDetailSearchWitness(reportId: Long): BaseResponse<DetailWitnessResponseDto>
}