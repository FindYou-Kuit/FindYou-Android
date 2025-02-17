package com.example.findu.presentation.ui.report.model

sealed interface GptUiState {
    object Default : GptUiState
    object Loading : GptUiState
    object Finished : GptUiState
}