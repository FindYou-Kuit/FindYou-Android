package com.example.findu.presentation.ui.report.missing.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.report.component.ReportDateComponent
import com.example.findu.presentation.ui.report.component.ReportDescriptionComponent
import com.example.findu.presentation.ui.report.component.ReportFurColorComponent
import com.example.findu.presentation.ui.report.component.ReportImageComponent
import com.example.findu.presentation.ui.report.component.ReportInputComponent
import com.example.findu.presentation.ui.report.missing.component.MissingAnimalInfoComponent
import com.example.findu.presentation.ui.report.missing.component.ReportGenderComponent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiState
import com.example.findu.ui.theme.FindUTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MissingReportScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {
    Column {
        FindUTopAppBar(
            modifier = Modifier,
            title = R.string.report_missing,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = { onEvent(MissingReportUiEvent.OnBackPressed) }
        )
        ReportImageComponent(
            imgUriList = uiState.imageUriList,
            onOpenDialogClick = { onEvent(MissingReportUiEvent.OnAddImageClick) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            VerticalSpacer(30.dp)
            MissingAnimalInfoComponent(
                speciesType = uiState.speciesType,
                breed = uiState.breed?.name,
                age = uiState.age.text.toString().toIntOrNull(),
                onSelectAnimalInfoClick = { onEvent(MissingReportUiEvent.OnSelectAnimalInfoClick) }
            )
            VerticalSpacer(30.dp)
            ReportGenderComponent(
                selectedGender = uiState.gender,
                onGenderSelected = { onEvent(MissingReportUiEvent.OnGenderSelected(it)) }
            )
            VerticalSpacer(30.dp)
            ReportInputComponent(
                titleRes = R.string.report_rfid_title,
                placeHolderRes = R.string.report_rfid_placeholder,
                state = uiState.rfidNumber,
                onKeyboardAction = KeyboardActionHandler {
                    onEvent(MissingReportUiEvent.OnDismissKeyboard)
                }
            )
            VerticalSpacer(30.dp)
            ReportFurColorComponent(
                selectedFurColors = uiState.selectedFurColors,
                onColorSelected = { color, isSelected ->
                    onEvent(MissingReportUiEvent.OnFurColorSelected(color, isSelected))
                }
            )
            VerticalSpacer(30.dp)
            ReportDateComponent(
                selectedDate = uiState.missingDate,
                titleRes = R.string.report_missing_date_title,
                nowDate = uiState.nowDate.format(
                    DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN)
                ),
                onClick = { onEvent(MissingReportUiEvent.OnMissingDateClicked) }
            )
            VerticalSpacer(30.dp)
            ReportDescriptionComponent(
                descriptionState = uiState.description,
                onKeyboardAction = KeyboardActionHandler {
                    onEvent(MissingReportUiEvent.OnDismissKeyboard)
                }
            )
            VerticalSpacer(30.dp)
        }
    }
}

@Preview(showBackground = true, heightDp = 1600)
@Composable
private fun MissingReportScreenPreview() {
    FindUTheme {
        MissingReportScreen(
            uiState = MissingReportUiState(),
            onEvent = {}
        )
    }
}