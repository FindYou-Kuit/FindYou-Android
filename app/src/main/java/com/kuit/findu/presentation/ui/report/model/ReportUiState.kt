package com.kuit.findu.presentation.ui.report.model

sealed interface ReportUiState {
    object Default : ReportUiState
    object Enable : ReportUiState
    object Loading : ReportUiState
    object Error : ReportUiState
    object Finished : ReportUiState
}