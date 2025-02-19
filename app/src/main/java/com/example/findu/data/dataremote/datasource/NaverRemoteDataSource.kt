package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.response.report.NaverResponseDto

interface NaverRemoteDataSource {
    suspend fun getAddress(
        coords: String
    ) : NaverResponseDto
}