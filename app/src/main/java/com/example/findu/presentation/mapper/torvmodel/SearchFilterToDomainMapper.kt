package com.example.findu.presentation.mapper.torvmodel

import com.example.findu.domain.model.search.SearchFilterData
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel

fun SearchFilterUiModel.toDomain() = SearchFilterData(
    breeds = breeds,
    location = location,
    species = species,
    startDate = startDate,
    endDate = endDate
)