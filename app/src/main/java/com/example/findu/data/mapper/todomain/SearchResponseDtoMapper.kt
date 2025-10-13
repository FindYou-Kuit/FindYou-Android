package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.search.SearchAnimalCard
import com.example.findu.data.dataremote.model.response.search.SearchResponseDto
import com.example.findu.data.dataremote.model.response.SearchAnimalCard
import com.example.findu.data.dataremote.model.response.SearchResponseDto
import com.example.findu.data.mapper.todomain.home.toDomain
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.domain.model.search.SearchData
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.model.SearchRvTag

fun SearchResponseDto.toDomain(): List<SearchData> {
    return listOf(
        SearchData(
            cards = this.cards.map { it.toDomain() },
            lastId = this.lastId ?: -1,
            isLast = this.isLast
        )
    )
}

fun SearchAnimalCard.toDomain(): SearchAnimal {
    return SearchAnimal(
        reportId = this.reportId,
        thumbnailImageUrl = this.thumbnailImageUrl ?: "",
        title = this.title,
        tag = this.tag.toSearchStatus(),
        date = this.date,
        location = this.location,
        interest = this.interest,
    )
}


fun String.toSearchStatus(): SearchStatus {
    return when (this.trim()) {
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