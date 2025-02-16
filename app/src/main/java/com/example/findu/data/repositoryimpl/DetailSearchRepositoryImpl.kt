package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.DetailSearchRemoteDataSource
import com.example.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.search.DetailSearchData
import com.example.findu.domain.repository.DetailSearchRepository
import com.example.findu.domain.repository.HomeRepository
import javax.inject.Inject

class DetailSearchRepositoryImpl @Inject constructor(
    private val detailSearchRemoteDataSource: DetailSearchRemoteDataSource
) : DetailSearchRepository {
    override suspend fun getDetailSearchProtect(): Result<DetailSearchData> =
        runCatching {
            detailSearchRemoteDataSource.getDetailSearchProtect().handleBaseResponse().getOrThrow().toDomain()
        }
    override suspend fun getDetailSearchReport(): Result<DetailSearchData> =
        runCatching {
            detailSearchRemoteDataSource.getDetailSearchReport().handleBaseResponse().getOrThrow().toDomain()
        }
}
