package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.data.dataremote.service.SearchService
import com.example.findu.domain.model.search.SearchFilterData
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchRemoteDataSource {
    override suspend fun getSearchAll(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long,
        lastReportId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchAll(
            startDate = searchFilterData?.startDate,
            endDate = searchFilterData?.endDate,
            species = searchFilterData?.species,
            breeds = searchFilterData?.breeds?.joinToString(","),
            location = searchFilterData?.location,
            lastProtectId = lastProtectId,
            lastReportId = lastReportId
        )

    override suspend fun getSearchReport(
        searchFilterData: SearchFilterData?,
        lastReportId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchReport(
            startDate = searchFilterData?.startDate,
            endDate = searchFilterData?.endDate,
            species = searchFilterData?.species,
            breeds = searchFilterData?.breeds?.joinToString(","),
            location = searchFilterData?.location,
            lastReportId = lastReportId
        )

    override suspend fun getSearchProtect(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchProtect(
            startDate = searchFilterData?.startDate,
            endDate = searchFilterData?.endDate,
            species = searchFilterData?.species,
            breeds = searchFilterData?.breeds?.joinToString(","),
            location = searchFilterData?.location,
            lastProtectId = lastProtectId
        )
}