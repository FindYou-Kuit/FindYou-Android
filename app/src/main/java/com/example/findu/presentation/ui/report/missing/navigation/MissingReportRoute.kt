package com.example.findu.presentation.ui.report.missing.navigation

import kotlinx.serialization.Serializable

sealed interface MissingReportRoute {
    @Serializable
    data object MissingReport : MissingReportRoute

    @Serializable
    data object AnimalInfo : MissingReportRoute
}