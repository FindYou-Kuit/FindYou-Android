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
    override suspend fun getSearchAll(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long,
        lastReportId: Long
    ): Result<List<SearchData>> =
        runCatching {
            listOf(
                searchRemoteDataSource.getSearchAll(
                    startDate = startDate,
                    endDate = endDate,
                    species = species,
                    breeds = breeds,
                    location = location,
                    lastProtectId,
                    lastReportId
                ).handleBaseResponse().getOrThrow().toDomain()
            )
        }

    override suspend fun getSearchReport(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastReportId: Long
    ): Result<List<SearchData>> =
        runCatching {
            listOf(
                searchRemoteDataSource.getSearchReport(
                    startDate = startDate,
                    endDate = endDate,
                    species = species,
                    breeds = breeds,
                    location = location,
                    lastReportId = lastReportId
                ).handleBaseResponse()
                    .getOrThrow().toDomain()
            )
        }

    override suspend fun getSearchProtect(
        startDate: String?,
        endDate: String?,
        species: String?,
        breeds: String?,
        location: String?,
        lastProtectId: Long
    ): Result<List<SearchData>> =
        runCatching {
            listOf(
                searchRemoteDataSource.getSearchProtect(
                    startDate = startDate,
                    endDate = endDate,
                    species = species,
                    breeds = breeds,
                    location = location,
                    lastProtectId = lastProtectId
                ).handleBaseResponse()
                    .getOrThrow().toDomain()
            )
        }
}
