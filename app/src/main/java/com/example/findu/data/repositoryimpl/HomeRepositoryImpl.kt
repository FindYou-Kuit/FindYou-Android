package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.HomeRemoteDataSource
import com.example.findu.data.dataremote.util.handleBaseResponse
import com.example.findu.data.mapper.todomain.home.toDomain
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.repository.HomeRepository
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