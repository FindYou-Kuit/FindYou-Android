package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.kuit.findu.data.dataremote.util.handleBaseResponse
import com.kuit.findu.data.mapper.todomain.toDomain
import com.kuit.findu.domain.model.HomeData
import com.kuit.findu.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getHome( lat: Double?,
                                  lon: Double?): Result<HomeData> =
        runCatching {
            homeRemoteDataSource.getHome(lat,lon).handleBaseResponse().getOrThrow().toDomain()
        }
}