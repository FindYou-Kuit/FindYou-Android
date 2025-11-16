package com.kuit.findu.domain.model.report

import com.kuit.findu.domain.model.breed.SpeciesType

data class GptData(
    val breed: String = "",
    val species: SpeciesType? = null,
    val furColors: List<FurColorType> = listOf()
)
