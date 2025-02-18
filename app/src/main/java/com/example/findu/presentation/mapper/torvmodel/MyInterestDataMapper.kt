package com.example.findu.presentation.mapper.torvmodel

import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.presentation.model.MyInterestRv
import com.example.findu.presentation.model.MyReportHistoryRv

fun MyInterestData.InterestAnimal.toRvModel(): MyInterestRv {
    return MyInterestRv(
        animalId = this.animalId,
        thumbnailImageUrl = this.thumbnailImageUrl,
        title = this.title,
        tag = this.tag,
        date = this.date,
        location = this.location,
        interest = this.interest
    )
}


fun MyReportHistoryData.Report.toRvModel(): MyReportHistoryRv {
    return MyReportHistoryRv(
        reportId = this.reportId,
        thumbnailImageUrl = this.thumbnailImageUrl,
        title = this.title,
        tag = this.tag,
        date = this.date,
        location = this.location
    )
}
