package com.example.findu.presentation.mapper.todomain

import com.example.findu.domain.model.search.SearchFilterData
import com.example.findu.presentation.model.SearchFilters

fun SearchFilters.toDomain() = SearchFilterData(
    breeds = breeds,
    location = location,
    species = species,
    startDate = startDate,
    endDate = endDate
)