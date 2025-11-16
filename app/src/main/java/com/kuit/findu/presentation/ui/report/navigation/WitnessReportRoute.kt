package com.kuit.findu.presentation.ui.report.navigation

import kotlinx.serialization.Serializable

sealed interface WitnessReportRoute {
    @Serializable
    data object WitnessReport : WitnessReportRoute

    @Serializable
    data object AnimalInfo : WitnessReportRoute
}