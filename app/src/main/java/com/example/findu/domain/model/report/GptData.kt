package com.example.findu.domain.model.report

data class GptData(
    val breed: Breed,
    val species: SpeciesType,
    val furColors: List<FurColorType>
)
