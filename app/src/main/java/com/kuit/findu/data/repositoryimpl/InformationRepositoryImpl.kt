package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.dataremote.datasource.InformationRemoteDataSource
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.extra.toDomain
import com.kuit.findu.domain.model.extra.Center
import com.kuit.findu.domain.model.extra.Department
import com.kuit.findu.domain.model.extra.PagedResult
import com.kuit.findu.domain.model.extra.Sido
import com.kuit.findu.domain.model.extra.VolunteerWork
import com.kuit.findu.domain.repository.InformationRepository
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
        district: String?,
        lat: Double?,
        long: Double?,
        size: Int?
    ): Result<PagedResult<Center>> = runCatching {
        remote.getCenters(lastId, district, lat, long, size)
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

    override suspend fun getSigungu(sidoId: Long): Result<List<String>> = runCatching {
        remote.getSigungu(sidoId = sidoId)
            .handleBaseResponse()
            .getOrThrow()
            .sigunguList
    }

}