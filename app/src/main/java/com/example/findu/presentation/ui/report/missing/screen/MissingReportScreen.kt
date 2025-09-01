package com.example.findu.presentation.ui.report.missing.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.report.missing.component.MissingAnimalInfoComponent
import com.example.findu.presentation.ui.report.component.ReportImageComponent
import com.example.findu.presentation.ui.report.missing.component.RFIDComponent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiState
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEffect
import kotlinx.coroutines.flow.Flow
import com.example.findu.ui.theme.FindUTheme

@Composable
fun MissingReportScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
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
        VerticalSpacer(30.dp)
        MissingAnimalInfoComponent(
            speciesType = uiState.speciesType,
            breed = uiState.breed?.name,
            age = uiState.age.text.toString().toIntOrNull(),
            onSelectAnimalInfoClick = { onEvent(MissingReportUiEvent.OnSelectAnimalInfoClick) }
        )
        VerticalSpacer(30.dp)
        RFIDComponent(
            rfidState = uiState.rfidNumber,
            onKeyboardAction = KeyboardActionHandler {
                onEvent(MissingReportUiEvent.OnDismissKeyboard)
            }
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