package com.example.findu.domain.model.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class MyViewedAnimalData(
    val isLast: Boolean,
    val lastViewedProtectId: Long,
    val lastViewedReportId: Long,
    val viewedAnimals: List<ViewedAnimal>
) {
    data class ViewedAnimal(
        val cardId: Long,
        val date: String,
        val interest: Boolean,
        val location: String,
        val tag: String,
        val thumbnailImageUrl: String,
        val title: String
    )
}