package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.home.HomeResponseDto
import com.kuit.findu.data.dataremote.service.HomeService
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeRemoteDataSource {
    override suspend fun getHome(
        lat: Double?,
        lon: Double?,
    ): BaseResponse<HomeResponseDto> =
        homeService.getHome(lat, lon)
}