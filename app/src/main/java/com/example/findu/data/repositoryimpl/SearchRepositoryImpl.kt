package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.toDomain
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {
    override suspend fun getSearch(): Result<SearchData> =
        runCatching {
            searchRemoteDataSource.getSearch().handleBaseResponse().getOrThrow().toDomain()
        }
}