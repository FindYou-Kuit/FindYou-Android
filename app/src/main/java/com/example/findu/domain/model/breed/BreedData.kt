package com.example.findu.domain.model.breed

data class BreedData(
    val dogBreedList: List<Breed.DogBreed> = emptyList(),
    val catBreedList: List<Breed.CatBreed> = emptyList(),
    val etcBreedList: List<Breed.EtcBreed> = emptyList(),
)

sealed class Breed(
    val name: String,
    val id: Int = 0,
) {
    data class DogBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType,
    ) : Breed(breedName, breedId)

    data class CatBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType,
    ) : Breed(breedName, breedId)

    data class EtcBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType,
    ) : Breed(breedName, breedId)
}


