package com.example.findu.data.mapper.toDomain

import com.example.findu.data.dataremote.model.response.search.DetailMissingResponseDto
import com.example.findu.data.dataremote.model.response.search.DetailProtectResponseDto
import com.example.findu.data.dataremote.model.response.search.DetailWitnessResponseDto
import com.example.findu.domain.model.search.DetailMissingData
import com.example.findu.domain.model.search.DetailProtectData
import com.example.findu.domain.model.search.DetailWitnessData
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.search.model.SearchRvTag

fun DetailProtectResponseDto.toDomain() = DetailProtectData(
<<<<<<< HEAD
    imageUrls = imageUrls,
    breed = breed,
    tag = tag,
    age = age,
    weight = weight,
    furColor = furColor,
    sex = sex,
    neutering = neutering,
    significant = significant,
    careName = careName,
    careAddr = careAddr,
    latitude = latitude,
    longitude = longitude,
    careTel = careTel,
    foundDate = foundDate,
    foundLocation = foundLocation,
    noticeDuration = noticeDuration,
    noticeNumber = noticeNumber,
    authority = authority,
    interest = interest
)

fun DetailMissingResponseDto.toDomain() = DetailMissingData(
    imageUrls = imageUrls,
    breed = breed,
    tag = tag,
    age = age,
    sex = sex,
    missingDate = missingDate,
    rfid = rfid,
    significant = significant,
    missingLocation = missingLocation,
    missingAddress = missingAddress,
    latitude = latitude,
    longitude = longitude,
    reporterName = reporterName,
    reporterTel = reporterTel,
    interest = interest
)

fun DetailWitnessResponseDto.toDomain() = DetailWitnessData(
    imageUrls = imageUrls,
    breed = breed,
    tag = tag,
    furColor = furColor,
    significant = significant,
    witnessLocation = witnessLocation,
    witnessAddress = witnessAddress,
    latitude = latitude,
    longitude = longitude,
    reporterInfo = reporterInfo,
    witnessDate = witnessDate,
    interest = interest
=======
    imageUrl = this.imageUrl,
    breed = this.breed,
    tag = this.tag.toDetailSearchStatus(),
    age = this.age,
    weight = this.weight,
    sex = this.sex,
    happenDate = this.happenDate,
    furColor = this.furColor,
    neutering = this.neutering,
    specialNote = this.significant,
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
    foundLocation = this.foundLocation ?: "위치 정보 없음",
    features = this.features,
    specialNote = this.additionalDescription,
    interest = this.interest,
    age = this.age ?: "정보 없음",
    rfid = this.rfid ?: "정보 없음",
    userPhone = this.userPhone ?: "정보 없음",
    surroundPlace = this.surroundPlace ?: "정보 없음"
>>>>>>> develop
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

