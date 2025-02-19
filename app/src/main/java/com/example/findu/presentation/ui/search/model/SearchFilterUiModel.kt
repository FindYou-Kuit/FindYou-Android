package com.example.findu.presentation.ui.search.model

import com.example.findu.presentation.model.SearchFilters
import java.io.Serializable

data class SearchFilterUiModel(
    var startDate: String?,
    var endDate: String?,
    var species: String?,
    var breeds: List<String>?,
    var location: String?
): Serializable

fun SearchFilterUiModel.toSearchFilters(): SearchFilters {
    return SearchFilters(
        startDate = startDate,
        endDate = endDate,
        species = species,
        breeds = breeds,
        location = location
    )
}
