package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.toDomain
import com.kuit.findu.domain.model.search.DetailMissingData
import com.kuit.findu.domain.model.search.DetailProtectData
import com.kuit.findu.domain.model.search.DetailWitnessData
import com.kuit.findu.domain.repository.DetailSearchRepository
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
