package com.example.findu.data.mapper.todomain

import com.example.findu.data.dataremote.model.response.DetailSearchResponseDto
import com.example.findu.data.dataremote.model.response.HomeResponseDto
import com.example.findu.data.dataremote.model.response.ProtectAnimalCard
import com.example.findu.data.dataremote.model.response.ReportAnimalCard
import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal
import com.example.findu.domain.model.search.DetailSearchData

fun DetailSearchResponseDto.toDomain() = DetailSearchData(
    imageUrl = this.imageUrl,
    breed = this.breed,
    tag = this.tag,
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