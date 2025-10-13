package com.example.findu.presentation.ui.search.model

import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.search.SearchFilterData
import java.io.Serializable

data class SearchFilterUiModel(
    var startDate: String? = null,
    var endDate: String? = null,
    var species: String? = null,
    var breeds: List<String>? = null,
    var location: String? = null,
) : Serializable

fun SearchFilterUiModel.toDomain(): SearchFilterData {
    val koreanSpecies = when (this.species) {
        SpeciesType.DOG.name -> SpeciesType.DOG.displayName
        SpeciesType.CAT.name -> SpeciesType.CAT.displayName
        SpeciesType.ETC.name -> SpeciesType.ETC.displayName
        else -> null
    }
    return SearchFilterData(
        startDate = this.startDate,
        endDate = this.endDate,
        species = koreanSpecies,
        breeds = this.breeds,
        location = this.location
    )
}