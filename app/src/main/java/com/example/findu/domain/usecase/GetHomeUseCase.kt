package com.example.findu.domain.usecase

import com.example.findu.domain.model.HomeData
import com.example.findu.domain.model.HomeReportData
import com.example.findu.domain.model.ReportDataType
import com.example.findu.domain.model.ReportItem
import com.example.findu.domain.repository.HomeRepository

class GetHomeUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<HomeData> =
        homeRepository.getHome().map { homeData ->
            homeData
        }

    fun HomeData.toHomeReportData(): HomeReportData {
        return HomeReportData(
            reports = listOf(
                ReportItem(
                    type = ReportDataType.RESCUE,
                    count = this.todayRescuedAnimalCount
                ),
                ReportItem(
                    type = ReportDataType.PROTECTION,
                    count = 0
                ),
                ReportItem(
                    type = ReportDataType.ADOPTION,
                    count = 0
                ),
                ReportItem(
                    type = ReportDataType.REPORT,
                    count = this.todayReportAnimalCount
                )
            )
        )
    }
}