package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.NaverRemoteDataSource
import com.example.findu.data.dataremote.model.response.report.GeocodeResponseDto
import com.example.findu.data.dataremote.model.response.report.ReverseGeocodeResponseDto
import com.example.findu.data.dataremote.service.NaverService
import javax.inject.Inject

class NaverRemoteDataSourceImpl @Inject constructor(
    private val naverService: NaverService
): NaverRemoteDataSource {
    override suspend fun getAddress(coords: String): ReverseGeocodeResponseDto =
        naverService.getAddress(coords = coords)

    override suspend fun getLatLng(address: String): GeocodeResponseDto =
        naverService.getLatLng(address = address)
}