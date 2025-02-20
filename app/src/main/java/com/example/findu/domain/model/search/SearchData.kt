package com.example.findu.domain.model.search

import java.io.Serializable

data class SearchData(
    val cards: List<SearchAnimal>,
    val lastProtectId: Long,
    val lastReportId: Long,
    val isLast: Boolean
) : Serializable

data class SearchAnimal(
    val cardId: Long,
    val thumbnailImageUrl: String,
    val title: String,
    val tag: SearchStatus,
    val date: String,
    val location: String,
    val interest: Boolean
) : Serializable
