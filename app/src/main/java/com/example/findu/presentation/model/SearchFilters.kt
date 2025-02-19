package com.example.findu.presentation.model

data class SearchFilters(
    var breeds: List<String>? = null,
    var location: String? = null,
    var species: String? = null,
    var startDate: String? = null,
    var endDate: String? = null
)
