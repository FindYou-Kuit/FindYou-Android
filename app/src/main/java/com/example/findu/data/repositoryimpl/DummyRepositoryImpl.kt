package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.example.findu.data.mapper.toDomainModel
import com.example.findu.domain.model.DummyData
import com.example.findu.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource
) : DummyRepository {
    override suspend fun dummy(): Result<DummyData> = runCatching {
        dummyRemoteDataSource.dummy().toDomainModel()
    }
}