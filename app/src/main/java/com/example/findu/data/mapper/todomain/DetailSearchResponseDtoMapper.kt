package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.DetailProtectResponseDto
import com.example.findu.data.dataremote.model.response.DetailReportResponseDto
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailReportData
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.model.SearchRvTag

fun DetailProtectResponseDto.toDomain() = DetailProtectData(
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

fun DetailReportResponseDto.toDomain() = DetailReportData(
    imageUrls = this.imageUrls,
    breed = this.breed,
    tag = this.tag.toDetailSearchStatus(),
    sex = this.sex ?: "정보 없음",
    furColor = this.furColor,
    userName = this.userName,
    writeDate = this.writeDate,
    eventDate = this.eventDate,
    eventLocation = this.eventLocation ?: "위치 정보 없음",
    foundLocation = this.foundLocation?:"위치 정보 없음",
    features = this.features,
    additionalDescription = this.additionalDescription,
    interest = this.interest,
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

