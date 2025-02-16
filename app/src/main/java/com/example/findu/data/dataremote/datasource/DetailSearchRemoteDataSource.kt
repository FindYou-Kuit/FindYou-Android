package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailSearchResponseDto

interface DetailSearchRemoteDataSource {
    suspend fun getDetailSearchProtect(): BaseResponse<DetailSearchResponseDto>
    suspend fun getDetailSearchReport(): BaseResponse<DetailSearchResponseDto>

}