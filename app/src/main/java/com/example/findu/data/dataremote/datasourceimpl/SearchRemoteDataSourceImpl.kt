package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.data.dataremote.service.SearchService
import retrofit2.http.Query
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchRemoteDataSource {
    override suspend fun getSearchAll(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long,
        lastReportId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchAll(
            startDate = startDate,
            endDate = endDate,
            species = species,
            breeds = breeds,
            location = location,
            lastProtectId = lastProtectId,
            lastReportId = lastReportId
        )

    override suspend fun getSearchReport(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastReportId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchReport(
            startDate = startDate,
            endDate = endDate,
            species = species,
            breeds = breeds,
            location = location,
            lastReportId = lastReportId
        )

    override suspend fun getSearchProtect(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchProtect(
            startDate = startDate,
            endDate = endDate,
            species = species,
            breeds = breeds,
            location = location,
            lastProtectId = lastProtectId
        )
}