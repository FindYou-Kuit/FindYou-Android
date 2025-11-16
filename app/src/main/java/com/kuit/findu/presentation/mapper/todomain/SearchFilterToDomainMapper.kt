package com.kuit.findu.presentation.mapper.todomain

import com.kuit.findu.domain.model.search.SearchFilterData
import com.kuit.findu.presentation.ui.search.model.SearchFilterUiModel

fun SearchFilterUiModel.toDomain() = SearchFilterData(
    breeds = breeds,
    location = location,
    species = species,
    startDate = startDate,
    endDate = endDate
)