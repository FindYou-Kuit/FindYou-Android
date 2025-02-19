package com.example.findu.presentation.ui.search.model

import java.io.Serializable

data class SearchFilterUiModel(
    var startDate: String? = null,
    var endDate: String? = null,
    var species: String? = null,
    var breeds: List<String>? = null,
    var location: String? = null
) : Serializable

fun SearchFilterUiModel.toSearchFilterUiModel(): SearchFilterUiModel {
    return SearchFilterUiModel(
        startDate = startDate,
        endDate = endDate,
        species = species,
        breeds = breeds,
        location = location
    )
}
