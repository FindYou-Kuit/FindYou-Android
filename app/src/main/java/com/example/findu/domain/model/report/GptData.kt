package com.example.findu.domain.model.report

data class GptData(
    val breed: Breed? = null,
    val species: SpeciesType? = null,
    val furColors: List<FurColorType> = listOf()
)
