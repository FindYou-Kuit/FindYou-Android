package com.example.findu.domain.model

import com.example.findu.domain.model.report.Breed
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.domain.model.report.SpeciesType

data class GptData(
    val breed: Breed,
    val species: SpeciesType,
    val furColors: List<FurColorType>
)
