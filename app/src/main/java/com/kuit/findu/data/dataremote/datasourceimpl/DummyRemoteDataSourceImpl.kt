package com.kuit.findu.data.dataremote.datasourceimpl

import com.kuit.findu.data.dataremote.datasource.DummyRemoteDataSource
import com.kuit.findu.data.dataremote.model.response.DummyResponseDto
import com.kuit.findu.data.dataremote.service.DummyService
import javax.inject.Inject

class DummyRemoteDataSourceImpl @Inject constructor(
    private val service: DummyService
) : DummyRemoteDataSource {
    override suspend fun dummy(): DummyResponseDto =
        service.dummy()
}