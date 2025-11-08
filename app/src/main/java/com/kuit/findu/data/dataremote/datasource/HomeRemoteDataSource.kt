package com.kuit.findu.data.dataremote.datasource

import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.home.HomeResponseDto

interface HomeRemoteDataSource {
    suspend fun getHome(
        lat: Double? = null,
        lon: Double? = null,
    ): BaseResponse<HomeResponseDto>
}