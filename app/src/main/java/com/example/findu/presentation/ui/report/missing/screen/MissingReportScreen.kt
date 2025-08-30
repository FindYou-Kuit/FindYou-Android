package com.example.findu.presentation.ui.report.missing.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.report.missing.component.ReportImageComponent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiState
import com.example.findu.ui.theme.FindUTheme

@Composable
fun MissingReportScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        FindUTopAppBar(
            modifier = Modifier.shadow(2.dp),
            title = R.string.report_missing,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = { onEvent(MissingReportUiEvent.OnBackPressed) }
        )
        ReportImageComponent(
            imgUriList = uiState.imageUriList,
            onOpenDialogClick = { onEvent(MissingReportUiEvent.OnAddImageClick) }
        )
    }
}

@Preview
@Composable
private fun MissingReportScreenPreview() {
    FindUTheme {
//        MissingReportScreen()
    }
}