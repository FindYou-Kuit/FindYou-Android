package com.example.findu.domain.model.breed

data class BreedData(
    val dogList : List<DogData> = emptyList(),
    val catList : List<CatData> = emptyList(),
    val etcList : List<EtcData> = emptyList()
)

data class CatData(
    val breedId: Int,
    val breedName: String,
    val species: SpeciesType
)

data class DogData(
    val breedId: Int,
    val breedName: String,
    val species: SpeciesType
)

data class EtcData(
    val breedId: Int,
    val breedName: String,
    val species: SpeciesType
)
