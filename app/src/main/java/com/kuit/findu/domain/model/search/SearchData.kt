package com.kuit.findu.domain.model.search

import java.io.Serializable

data class SearchData(
    val cards: List<SearchAnimal>,
    val lastId: Long,
    val isLast: Boolean,
) : Serializable

data class SearchAnimal(
    val reportId: Long,
    val thumbnailImageUrl: String?,
    val title: String,
    val tag: SearchStatus,
    val date: String,
    val location: String,
    val interest: Boolean,
    val createdAt: String,

) : Serializable

fun String.toSearchStatus(): SearchStatus {
    return when (this) {
        "실종신고" -> SearchStatus.MISSING
        "보호중" -> SearchStatus.PROTECTING
        "목격신고" -> SearchStatus.WITNESS
        else -> throw IllegalArgumentException("Unknown tag: $this")
    }
}