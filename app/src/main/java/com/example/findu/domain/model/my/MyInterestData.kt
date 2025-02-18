package com.example.findu.domain.model.my

data class MyInterestData(
    val interestAnimals: List<InterestAnimal>,
    val isLast: Boolean,
    val lastInterestProtectId: Int,
    val lastInterestReportId: Int
) {
    data class InterestAnimal(
        val animalId: Int,
        val date: String,
        val interest: Boolean,
        val interestId: Int,
        val isProtectingAnimal: Boolean,
        val location: String,
        val tag: String,
        val thumbnailImageUrl: String,
        val title: String
    )
}
