package com.example.findu.data.dataremote.datasourceimpl

import com.example.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.example.findu.data.dataremote.model.response.DummyResponseDto
import com.example.findu.data.dataremote.service.DummyService
import javax.inject.Inject

class DummyRemoteDataSourceImpl @Inject constructor(
    private val service: DummyService
) : DummyRemoteDataSource {
    override suspend fun dummy(): DummyResponseDto =
        service.dummy()
}