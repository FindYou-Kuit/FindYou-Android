package com.example.findu.presentation.mapper.todomain

import com.example.findu.domain.model.my.MyInterestData
import com.example.findu.domain.model.my.MyReportHistoryData
import com.example.findu.domain.model.my.MyViewedAnimalData
import com.example.findu.presentation.model.MyInterestRv
import com.example.findu.presentation.model.MyReportHistoryRv
import com.example.findu.presentation.model.MyViewedAnimalsRv

fun MyInterestData.InterestAnimal.toRvModel(): MyInterestRv {
    return MyInterestRv(
        reportId = this.reportId,
        thumbnailImageUrl = this.thumbnailImageUrl,
        title = this.title,
        tag = this.tag,
        date = this.date,
        address = this.address,
        interest = true
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


fun MyViewedAnimalData.Card.toRvModel(): MyViewedAnimalsRv {
    return MyViewedAnimalsRv(
        reportId = this.reportId,
        thumbnailImageUrl = this.thumbnailImageUrl,
        title = this.title,
        tag = this.tag,
        date = this.date,
        location = this.location,
        interest = this.interest
    )

}
