package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.home.HomeResponseDto

interface HomeRemoteDataSource {
    suspend fun getHome(
        lat: Double? = null,
        lon: Double? = null
    ): BaseResponse<HomeResponseDto>
}