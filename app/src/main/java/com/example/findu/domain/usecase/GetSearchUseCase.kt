package com.example.findu.domain.usecase

import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.repository.SearchRepository

class GetSearchUseCase(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(): Result<SearchData> =
        searchRepository.getSearch()
}