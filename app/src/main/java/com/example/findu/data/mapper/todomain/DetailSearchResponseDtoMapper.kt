package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.DetailSearchResponseDto
import com.example.findu.data.dataremote.model.response.HomeResponseDto
import com.example.findu.data.dataremote.model.response.ProtectAnimalCard
import com.example.findu.data.dataremote.model.response.ReportAnimalCard
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal
import com.example.findu.domain.model.search.DetailSearchData
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.model.SearchRvTag

fun DetailSearchResponseDto.toDomain() = DetailSearchData(
    imageUrl = this.imageUrl,
    breed = this.breed,
    tag = this.tag.toDetailSearchStatus(),
    age = this.age,
    weight = this.weight,
    sex = this.sex,
    happenDate = this.happenDate,
    furColor = this.furColor,
    neutering = this.neutering,
    significant = this.significant,
    noticeNumber = this.noticeNumber,
    noticeDuration = this.noticeDuration,
    foundLocation = this.foundLocation,
    careName = this.careName,
    careAddr = this.careAddr,
    careTel = this.careTel,
    authority = this.authority,
    authorityPhoneNumber = this.authorityPhoneNumber,
    interest = this.interest
)

fun String.toDetailSearchStatus(): SearchStatus {
    return when (this) {
        "보호중" -> SearchStatus.PROTECTING
        "목격신고" -> SearchStatus.WITNESS
        "실종신고" -> SearchStatus.MISSING
        else -> throw IllegalArgumentException("Unknown tag value: $this")
    }
}
fun SearchStatus.toDetailSearchRvTag(): SearchRvTag {
    return when (this) {
        SearchStatus.PROTECTING -> SearchRvTag.PROTECTING
        SearchStatus.WITNESS -> SearchRvTag.WITNESS
        SearchStatus.MISSING -> SearchRvTag.MISSING
        SearchStatus.UNKNOWN -> SearchRvTag.UNKNOWN
    }
}

