package com.example.findu.domain.model.report

import com.example.findu.domain.model.breed.SpeciesType

data class GptData(
    val breed: String = "",
    val species: SpeciesType? = null,
    val furColors: List<FurColorType> = listOf()
)
