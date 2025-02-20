package com.example.findu.domain.model.search

data class SearchFilterData(
    var breeds: List<String>? = null,
    var location: String? = null,
    var species: String? = null,
    var startDate: String? = null,
    var endDate: String? = null
)