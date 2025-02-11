package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.HomeResponseDto
import com.example.findu.data.dataremote.service.HomeService
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeService: HomeService
): HomeRemoteDataSource {
    override suspend fun getHome(): BaseResponse<HomeResponseDto> =
        homeService.getHome()
}