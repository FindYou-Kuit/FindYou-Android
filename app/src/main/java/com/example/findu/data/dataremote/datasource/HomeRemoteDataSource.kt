package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.HomeResponseDto

interface HomeRemoteDataSource {
    suspend fun getHome(): BaseResponse<HomeResponseDto>
}