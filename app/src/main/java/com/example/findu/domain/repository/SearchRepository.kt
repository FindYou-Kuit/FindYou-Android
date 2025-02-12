package com.example.findu.domain.repository

import com.example.findu.domain.model.search.SearchData

interface SearchRepository {
    suspend fun getSearch(): Result<SearchData>
}