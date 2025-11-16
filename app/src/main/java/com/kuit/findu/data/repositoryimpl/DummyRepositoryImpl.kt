package com.kuit.findu.data.repositoryimpl

import com.kuit.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.kuit.findu.data.mapper.toDomainModel
import com.kuit.findu.domain.model.DummyData
import com.kuit.findu.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource
) : DummyRepository {
    override suspend fun dummy(): Result<DummyData> = runCatching {
        dummyRemoteDataSource.dummy().toDomainModel()
    }
}