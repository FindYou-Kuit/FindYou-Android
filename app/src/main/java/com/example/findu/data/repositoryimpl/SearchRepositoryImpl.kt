package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchFilterData
import com.example.findu.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun getSearchAll(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long,
        lastReportId: Long
    ): Result<List<SearchData>> =
        runCatching {
            listOf(
                searchRemoteDataSource.getSearchAll(
                    searchFilterData = searchFilterData,
                    lastProtectId = lastProtectId,
                    lastReportId = lastReportId
                ).handleBaseResponse().getOrThrow().toDomain()
            )
        }

    override suspend fun getSearchReport(
        searchFilterData: SearchFilterData?,
        lastReportId: Long
    ): Result<List<SearchData>> =
        runCatching {
            listOf(
                searchRemoteDataSource.getSearchReport(
                    searchFilterData = searchFilterData,
                    lastReportId = lastReportId
                ).handleBaseResponse()
                    .getOrThrow().toDomain()
            )
        }

    override suspend fun getSearchProtect(
        searchFilterData: SearchFilterData?,
        lastProtectId: Long
    ): Result<List<SearchData>> =
        runCatching {
            listOf(
                searchRemoteDataSource.getSearchProtect(
                    searchFilterData = searchFilterData,
                    lastProtectId = lastProtectId
                ).handleBaseResponse()
                    .getOrThrow().toDomain()
            )
        }
}
