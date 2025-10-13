package com.example.findu.data.mapper.todomain.report

import com.example.findu.data.dataremote.model.response.report.GeocodeResponseDto
import com.example.findu.data.dataremote.model.response.report.ReverseGeocodeResponseDto
import com.example.findu.domain.model.report.AddressData
import com.example.findu.domain.model.report.LatLngData

fun ReverseGeocodeResponseDto.toDomain() = AddressData(
    with(results[0]) {
        region.area1.name + " " + region.area2.name + " " + region.area3.name +
                " " + region.area4.name + " " + land.name + " " + land.number1
    })

fun GeocodeResponseDto.toDomain() = LatLngData(
    addresses[0].y.toDouble(),
    addresses[0].x.toDouble()
)