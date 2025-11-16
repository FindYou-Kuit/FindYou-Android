package com.kuit.findu.data.repositoryimpl

import android.util.Log
import com.kuit.findu.data.dataremote.datasource.SearchRemoteDataSource
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.toDomain
import com.kuit.findu.domain.model.search.SearchData
import com.kuit.findu.domain.model.search.SearchFilterData
import com.kuit.findu.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
) : SearchRepository {
    override suspend fun getReports(
        type: String,
        searchFilterData: SearchFilterData?,
        lastId: Long,
    ): Result<List<SearchData>> =
        runCatching {
            Log.d(
                "SearchRepositoryImpl",
                "서버 요청 발생"
            )
            searchRemoteDataSource.getReports(
                type = type,
                searchFilterData = searchFilterData,
                lastId = lastId
            ).handleBaseResponse().getOrThrow().toDomain()
        }
}
