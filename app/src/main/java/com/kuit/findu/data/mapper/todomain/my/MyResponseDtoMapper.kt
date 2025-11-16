package com.kuit.findu.data.mapper.todomain.my

import com.kuit.findu.data.dataremote.model.response.my.MyReportHistoryResponseDto
import com.kuit.findu.data.dataremote.model.response.my.MyViewedAnimalsResponseDto
import com.kuit.findu.domain.model.my.MyReportHistoryData
import com.kuit.findu.domain.model.my.MyViewedAnimalData


fun MyReportHistoryResponseDto.toDomain(): MyReportHistoryData {
    return MyReportHistoryData(
        reports = reports.map {
            MyReportHistoryData.Report(
                date = it.date,
                location = it.location,
                reportId = it.reportId,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title,
                interest = it.interest
            )
        },
        isLast = isLast,
        lastId = lastId,
    )
}

fun MyViewedAnimalsResponseDto.toDomain(): MyViewedAnimalData {
    return MyViewedAnimalData(
        cards = cards.map {
            MyViewedAnimalData.Card(
                reportId = it.reportId,
                date = it.date,
                interest = it.interest,
                location = it.location,
                tag = it.tag,
                thumbnailImageUrl = it.thumbnailImageUrl,
                title = it.title
            )
        },
        isLast = isLast,
        lastId = lastId,
    )
}