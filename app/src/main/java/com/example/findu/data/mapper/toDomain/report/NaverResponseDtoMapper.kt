package com.example.findu.data.mapper.todomain.report

import com.example.findu.data.dataremote.model.response.report.NaverResponseDto
import com.example.findu.domain.model.report.AddressData

fun NaverResponseDto.toDomain() = AddressData(
    with(results[0].region) {
        area1.name + " " + area2.name + " " + area3.name + " " + area4.name
    })
