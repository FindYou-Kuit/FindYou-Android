package com.example.findu.domain.repository

import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.search.DetailSearchData

interface DetailSearchRepository {
    suspend fun getDetailSearchProtect(): Result<DetailSearchData>
    suspend fun getDetailSearchReport(): Result<DetailSearchData>
}
