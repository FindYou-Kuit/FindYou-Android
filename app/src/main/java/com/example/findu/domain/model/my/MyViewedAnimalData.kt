package com.example.findu.domain.model.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class MyViewedAnimalData(
    val isLast: Boolean,
    val lastId: Long,
    val cards: List<Card>
) {
    data class Card(
        val reportId: Long,
        val thumbnailImageUrl: String,
        val title: String,
        val tag: String,
        val date: String,
        val location: String,
        val interest: Boolean,
    )
}