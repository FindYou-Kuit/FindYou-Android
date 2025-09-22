package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.toDomain.toDomain
import com.example.findu.domain.model.search.DetailMissingData
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailWitnessData
import com.example.findu.domain.repository.DetailSearchRepository
import javax.inject.Inject

class DetailSearchRepositoryImpl @Inject constructor(
    private val detailSearchRemoteDataSource: DetailSearchRemoteDataSource,
) : DetailSearchRepository {
    override suspend fun getDetailSearchProtect(reportId: Long): Result<DetailProtectData> =
        runCatching {
            detailSearchRemoteDataSource
                .getDetailSearchProtect(reportId)
                .handleBaseResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun getDetailSearchMissing(reportId: Long): Result<DetailMissingData> =
        runCatching {
            detailSearchRemoteDataSource
                .getDetailSearchMissing(reportId)
                .handleBaseResponse()
                .getOrThrow()
                .toDomain()
        }

    override suspend fun getDetailSearchWitness(reportId: Long): Result<DetailWitnessData> =
        runCatching {
            detailSearchRemoteDataSource
                .getDetailSearchWitness(reportId)
                .handleBaseResponse()
                .getOrThrow()
                .toDomain()
        }
}
