package com.example.findu.domain.model.search

import java.io.Serializable

data class SearchData(
    val cards: List<SearchAnimal>,
    val lastId: Long,
    val isLast: Boolean
) : Serializable

data class SearchAnimal(
    val reportId: Long,
    val thumbnailImageUrl: String?,
    val title: String,
    val tag: SearchStatus,
    val date: String,
    val address: String,
    val interest: Boolean
) : Serializable
