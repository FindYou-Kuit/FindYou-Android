package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.response.report.GeocodeResponseDto
import com.example.findu.data.dataremote.model.response.report.ReverseGeocodeResponseDto

interface NaverRemoteDataSource {
    suspend fun getAddress(
        coords: String
    ) : ReverseGeocodeResponseDto

    suspend fun getLatLng(
        address: String
    ) : GeocodeResponseDto
}