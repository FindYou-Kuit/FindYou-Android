package com.example.findu.domain.model.my

data class MyInterestData(
    val interestAnimals: List<InterestAnimal>,
    val isLast: Boolean,
    val lastInterestProtectId: Long,
    val lastInterestReportId: Long
) {
    data class InterestAnimal(
        val animalId: Long,
        val date: String,
        val interest: Boolean,
        val interestId: Long,
        val isProtectingAnimal: Boolean,
        val location: String,
        val tag: String,
        val thumbnailImageUrl: String,
        val title: String
    )
}
