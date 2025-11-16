package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.kuit.findu.data.dataremote.model.base.BaseResponse
import com.kuit.findu.data.dataremote.model.response.search.SearchResponseDto
import com.kuit.findu.data.dataremote.service.SearchService
import com.kuit.findu.domain.model.search.SearchFilterData
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService,
) : SearchRemoteDataSource {
    override suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long,
    ): BaseResponse<SearchResponseDto> =
        service.getReports(
            type = type,
            startDate = searchFilterData?.startDate,
            endDate = searchFilterData?.endDate,
            species = searchFilterData?.species,
            breeds = searchFilterData?.breeds?.joinToString(","),
            address = searchFilterData?.location,
            lastId = lastId
        )
}