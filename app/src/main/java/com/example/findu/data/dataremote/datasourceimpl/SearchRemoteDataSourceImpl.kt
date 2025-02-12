package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.data.dataremote.service.SearchService
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
private val service: SearchService
) : SearchRemoteDataSource {
    override suspend fun getSearch(): BaseResponse<SearchResponseDto> =
        service.getSearch()
}