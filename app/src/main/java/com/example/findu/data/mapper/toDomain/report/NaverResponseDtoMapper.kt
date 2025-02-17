package com.example.findu.data.mapper.todomain.report

import com.example.findu.data.dataremote.model.response.report.NaverResponseDto
import com.example.findu.domain.model.report.AddressData

fun NaverResponseDto.toDomain() = AddressData(
    with(results[0]) {
        region.area1.name + " " + region.area2.name + " " + region.area3.name +
                " " + region.area4.name + " " + land.name + " " + land.number1
    })
