package com.kuit.findu.domain.model.my

data class MyInterestData(
    val interestAnimals: List<InterestAnimal>,
    val isLast: Boolean,
    val lastId: Long
) {
    data class InterestAnimal(
        val reportId: Long,
        val thumbnailImageUrl: String,
        val title: String,
        val tag: String,
        val date: String,
        val address: String,
    )
}
