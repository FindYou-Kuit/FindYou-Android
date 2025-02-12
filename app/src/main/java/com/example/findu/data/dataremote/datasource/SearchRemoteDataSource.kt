package com.example.findu.data.dataremote.datasource

import com.example.findu.data.dataremote.model.base.BaseResponse
import com.example.findu.data.dataremote.model.response.SearchResponseDto

interface SearchRemoteDataSource {
    suspend fun getSearch(): BaseResponse<SearchResponseDto>
}