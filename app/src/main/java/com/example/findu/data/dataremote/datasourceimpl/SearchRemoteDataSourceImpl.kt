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
        @Query("lastProtectId") lastProtectId: Long,
        @Query("lastReportId") lastReportId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchAll(lastProtectId, lastReportId)

    override suspend fun getSearchReport(
        @Query("lastReportId") lastReportId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchReport(lastReportId)

    override suspend fun getSearchProtect(
        @Query("lastProtectId") lastProtectId: Long
    ): BaseResponse<SearchResponseDto> =
        service.getSearchProtect(lastProtectId)
}