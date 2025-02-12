package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchStatus

fun SearchResponseDto.toDomain() = SearchData(
    cardId = cardId,
    thumbnailImageUrl = thumbnailImageUrl,
    title = title,
    tag = tag.toSearchStatus(),
    date = date,
    location = location,
    interest = interest,
    lastProtectId = lastProtectId,
    lastReportId = lastReportId,
    isLast = isLast
)

fun String.toSearchStatus(): SearchStatus {
    return when (this) {
        "보호중" -> SearchStatus.PROTECTING
        "목격신고" -> SearchStatus.WITNESS
        "실종신고" -> SearchStatus.MISSING
        else -> throw IllegalArgumentException("Unknown tag value: $this")
    }
}
