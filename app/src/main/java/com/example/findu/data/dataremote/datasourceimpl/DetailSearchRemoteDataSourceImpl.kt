package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.example.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.DetailSearchResponseDto
import com.example.findu.data.dataremote.service.DetailSearchService
import javax.inject.Inject

class DetailSearchRemoteDataSourceImpl @Inject constructor(
    private val detailService: DetailSearchService
): DetailSearchRemoteDataSource {
    override suspend fun getDetailSearchReport(): BaseResponse<DetailSearchResponseDto> =
        detailService.getDetailSearchReport()
    override suspend fun getDetailSearchProtect(): BaseResponse<DetailSearchResponseDto> =
        detailService.getDetailSearchProtect()
}