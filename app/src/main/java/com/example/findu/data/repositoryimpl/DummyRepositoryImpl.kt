package com.example.findu.data.repositoryimpl

import com.example.findu.data.dataremote.datasource.remote.DummyRemoteDataSource
import com.example.findu.data.dataremote.model.response.DummyResponseDto
import com.example.findu.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource
) : DummyRepository {
    override suspend fun dummy(): Result<DummyResponseDto> = runCatching {
        dummyRemoteDataSource.dummy()
    }
}