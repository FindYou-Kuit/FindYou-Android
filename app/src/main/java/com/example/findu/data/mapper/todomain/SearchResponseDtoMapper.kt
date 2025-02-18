package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.SearchAnimalCard
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.model.SearchRvTag

fun SearchResponseDto.toDomain(): SearchData {
    return SearchData(
        cards = this.cards.map { it.toDomain() },
        lastProtectId = this.lastProtectId ?: -1,
        lastReportId = this.lastReportId ?: -1,
        isLast = this.isLast
    )
}

fun SearchAnimalCard.toDomain(): SearchAnimal {
    return SearchAnimal(
        cardId = this.cardId,
        thumbnailImageUrl = this.thumbnailImageUrl ?: "",
        title = this.title,
        tag = this.tag.toSearchStatus(),
        date = this.date,
        location = this.location,
        interest = this.interest,
    )
}


fun String.toSearchStatus(): SearchStatus {
    return when (this) {
        "보호중" -> SearchStatus.PROTECTING
        "목격신고" -> SearchStatus.WITNESS
        "실종신고" -> SearchStatus.MISSING
        else -> throw IllegalArgumentException("Unknown tag value: $this")
    }
}
fun SearchStatus.toSearchRvTag(): SearchRvTag {
    return when (this) {
        SearchStatus.PROTECTING -> SearchRvTag.PROTECTING
        SearchStatus.WITNESS -> SearchRvTag.WITNESS
        SearchStatus.MISSING -> SearchRvTag.MISSING
        SearchStatus.UNKNOWN -> SearchRvTag.UNKNOWN
    }
}

