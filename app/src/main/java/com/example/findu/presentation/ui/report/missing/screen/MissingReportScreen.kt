package com.example.findu.presentation.ui.report.missing.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiState
import com.example.findu.ui.theme.FindUTheme

@Composable
fun MissingReportScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {

}

@Preview
@Composable
private fun MissingReportScreenPreview() {
    FindUTheme {
//        MissingReportScreen()
    }
}