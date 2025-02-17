package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.repository.DetailSearchRepository
import javax.inject.Inject

class DetailSearchRepositoryImpl @Inject constructor(
    private val detailSearchRemoteDataSource: DetailSearchRemoteDataSource
) : DetailSearchRepository {
    override suspend fun getDetailSearchProtect(protectingReportId: Long): Result<DetailProtectData> =
        runCatching {
            detailSearchRemoteDataSource.getDetailSearchProtect(protectingReportId).handleBaseResponse().getOrThrow().toDomain()
        }
    override suspend fun getDetailSearchReport(reportId: Long): Result<DetailProtectData> =
        runCatching {
            detailSearchRemoteDataSource.getDetailSearchReport(reportId).handleBaseResponse().getOrThrow().toDomain()
        }
}
