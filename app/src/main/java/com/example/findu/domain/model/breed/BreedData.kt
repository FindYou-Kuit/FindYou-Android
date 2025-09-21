package com.example.findu.domain.model.breed

data class BreedData(
    val dogBreedList: List<Breed.DogBreed> = emptyList(),
    val catBreedList: List<Breed.CatBreed> = emptyList(),
    val etcBreedList: List<Breed.EtcBreed> = emptyList(),
)

sealed class Breed(val name: String) {

    data class DogBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType,
    ) : Breed(breedName)

    data class CatBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType,
    ) : Breed(breedName)

    data class EtcBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType,
    ) : Breed(breedName)
}


