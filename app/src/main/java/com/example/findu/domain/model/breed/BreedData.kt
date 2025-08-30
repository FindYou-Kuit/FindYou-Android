package com.example.findu.domain.model.breed

data class BreedData(
    val dogBreedList: List<Breed.DogBreed>,
    val catBreedList: List<Breed.CatBreed>,
    val etcBreedList: List<Breed.EtcBreed>
)

sealed class Breed(val name: String) {

    data class DogBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType
    ) : Breed(breedName)

    data class CatBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType
    ): Breed(breedName)

    data class EtcBreed(
        val breedId: Int,
        val breedName: String,
        val species: SpeciesType
    ): Breed(breedName)
}


