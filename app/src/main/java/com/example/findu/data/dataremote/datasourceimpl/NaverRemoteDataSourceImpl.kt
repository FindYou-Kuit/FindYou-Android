package com.example.findu.data.dataremote.datasourceimpl

import android.util.Log
import com.example.findu.data.dataremote.datasource.NaverRemoteDataSource
import com.example.findu.data.dataremote.model.response.report.NaverResponseDto
import com.example.findu.data.dataremote.service.NaverService
import javax.inject.Inject

class NaverRemoteDataSourceImpl @Inject constructor(
    private val naverService: NaverService
): NaverRemoteDataSource {
    override suspend fun getAddress(coords: String): NaverResponseDto =
        naverService.getAddress(coords = coords)
}