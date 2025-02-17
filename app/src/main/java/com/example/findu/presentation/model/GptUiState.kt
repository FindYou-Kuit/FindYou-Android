package com.example.findu.presentation.model

sealed interface GptUiState {
    object Default : GptUiState
    object Loading : GptUiState
    object Finished : GptUiState
}