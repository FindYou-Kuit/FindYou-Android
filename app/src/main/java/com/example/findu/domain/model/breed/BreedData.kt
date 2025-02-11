package com.example.findu.domain.model.breed

data class BreedData(
    val dogBreedList : List<DogBreedData>,
    val catBreedList : List<CatBreedData>,
    val etcBreedList : List<EtcBreedData>
) {
    data class DogBreedData(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType
    )

    data class CatBreedData(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType
    )

    data class EtcBreedData(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType
    )
}


