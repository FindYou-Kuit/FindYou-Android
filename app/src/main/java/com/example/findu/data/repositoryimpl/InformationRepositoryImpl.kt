package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.InformationRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.extra.toDomain
import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.Department
import com.example.findu.domain.model.extra.PagedResult
import com.example.findu.domain.model.extra.Sido
import com.example.findu.domain.model.extra.VolunteerWork
import com.example.findu.domain.repository.InformationRepository
import javax.inject.Inject

class InformationRepositoryImpl @Inject constructor(
    private val remote: InformationRemoteDataSource
) : InformationRepository {

    override suspend fun getVolunteers(
        lastId: Long?
    ): Result<PagedResult<VolunteerWork>> = runCatching {
        remote.getVolunteers(lastId)
            .handleBaseResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getCenters(
        lastId: Long?,
        sido: String?,
        sigungu: String?,
        lat: Double?,
        long: Double?
    ): Result<PagedResult<Center>> = runCatching {
        remote.getCenters(lastId, sido, sigungu, lat, long)
            .handleBaseResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getDepartments(
        district: String?,
        lastId: Long?
    ): Result<PagedResult<Department>> = runCatching {
        remote.getDepartments(district, lastId)
            .handleBaseResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getSido(): Result<List<Sido>> = runCatching {
        remote.getSido()
            .handleBaseResponse()
            .getOrThrow()
            .toDomain()
    }

    override suspend fun getSigungu(sidoId:Long): Result<List<String>> = runCatching {
        remote.getSigungu(sidoId=sidoId)
            .handleBaseResponse()
            .getOrThrow()
            .sigunguList
    }

}