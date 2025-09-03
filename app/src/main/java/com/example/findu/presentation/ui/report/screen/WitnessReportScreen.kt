package com.example.findu.presentation.ui.report.screen

import android.R.attr.bottom
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.presentation.ui.base.FindUButton
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.common.AppSettingDialog
import com.example.findu.presentation.ui.report.component.ReportDateComponent
import com.example.findu.presentation.ui.report.component.ReportDescriptionComponent
import com.example.findu.presentation.ui.report.component.ReportFurColorComponent
import com.example.findu.presentation.ui.report.component.ReportImageComponent
import com.example.findu.presentation.ui.report.component.ReportImageDialog
import com.example.findu.presentation.ui.report.component.ReportLocationComponent
import com.example.findu.presentation.ui.report.component.witness.WitnessAnimalInfoComponent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiState
import com.example.findu.ui.theme.FindUTheme
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.rememberCameraPositionState
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WitnessReportScreen(
    uiState: WitnessReportUiState,
    onEvent: (WitnessReportUiEvent) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        uiState.currentLatLng?.let {
            position = CameraPosition(it, 15.0)
        }
    }
    val buttonEnabled by remember(
        uiState.speciesType,
        uiState.breed,
        uiState.selectedFurColors,
        uiState.witnessDate,
        uiState.address,
        uiState.imageUriList
    ) {
        derivedStateOf {
            uiState.speciesType != null &&
                    uiState.breed != null &&
                    uiState.selectedFurColors.isNotEmpty() &&
                    uiState.witnessDate.isNotEmpty() &&
                    uiState.address.isNotEmpty() &&
                    uiState.imageUriList.isNotEmpty()
        }
    }

    WitnessReportScreen(
        uiState = uiState,
        onEvent = onEvent,
        cameraPositionState = cameraPositionState,
        buttonEnabled = buttonEnabled
    )

    if (uiState.isImageDialogShown) {
        ReportImageDialog(
            onDismissRequest = { onEvent(WitnessReportUiEvent.OnDismissDialog) },
            onCameraClick = { onEvent(WitnessReportUiEvent.OnOpenCameraClick) },
            onGalleryClick = { onEvent(WitnessReportUiEvent.OnOpenGalleryClick) }
        )
    }

    if (uiState.isAppSettingDialogShown) {
        AppSettingDialog(
            onDismissRequest = { onEvent(WitnessReportUiEvent.OnDismissDialog) },
            openAppSettings = { onEvent(WitnessReportUiEvent.OnAppSettingClick) }
        )
    }
}

@Composable
private fun WitnessReportScreen(
    uiState: WitnessReportUiState,
    onEvent: (WitnessReportUiEvent) -> Unit,
    cameraPositionState: CameraPositionState,
    buttonEnabled: Boolean,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 30.dp),
    ) {
        FindUTopAppBar(
            modifier = Modifier,
            title = R.string.report_witness,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = { onEvent(WitnessReportUiEvent.OnBackPressed) }
        )
        ReportImageComponent(
            imgUriList = uiState.imageUriList,
            onOpenDialogClick = { onEvent(WitnessReportUiEvent.OnAddImageClick) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            VerticalSpacer(30.dp)
            WitnessAnimalInfoComponent(
                speciesType = uiState.speciesType,
                breed = uiState.breed?.name,
                onSelectAnimalInfoClick = { onEvent(WitnessReportUiEvent.OnSelectAnimalInfoClick) }
            )
            VerticalSpacer(30.dp)
            ReportFurColorComponent(
                selectedFurColors = uiState.selectedFurColors,
                onColorSelected = { color, isSelected ->
                    onEvent(WitnessReportUiEvent.OnFurColorSelected(color, isSelected))
                }
            )
            VerticalSpacer(30.dp)
            ReportDateComponent(
                selectedDate = uiState.witnessDate,
                titleRes = R.string.report_witness_date_title,
                nowDate = uiState.nowDate.format(
                    DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN)
                ),
                onClick = { onEvent(WitnessReportUiEvent.OnWitnessDateClicked) }
            )
            VerticalSpacer(30.dp)
            ReportDescriptionComponent(
                descriptionState = uiState.description,
                onKeyboardAction = KeyboardActionHandler {
                    onEvent(WitnessReportUiEvent.OnDismissKeyboard)
                }
            )
            VerticalSpacer(30.dp)
            ReportLocationComponent(
                address = uiState.address,
                cameraPositionState = cameraPositionState,
                nearPlace = uiState.nearPlace,
                onAddressClick = { onEvent(WitnessReportUiEvent.OnAddressSearchClick) },
                dismissKeyboard = { onEvent(WitnessReportUiEvent.OnDismissKeyboard) }
            )
            VerticalSpacer(30.dp)
            FindUButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                textRes = R.string.my_done,
                onClick = { onEvent(WitnessReportUiEvent.OnReportFinishButtonClick) },
                enabled = buttonEnabled
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1400)
@Composable
private fun WitnessReportScreenPreview() {
    FindUTheme {
        WitnessReportScreen(
            uiState = WitnessReportUiState(
                speciesType = SpeciesType.DOG,
                breed = Breed.DogBreed(
                    breedId = 1,
                    breedName = "말티즈",
                    species = SpeciesType.DOG
                ),
                witnessDate = "2023년 10월 10일 (화)",
                selectedFurColors = listOf(FurColorType.OTHER),
                address = "서울시 강남구 역삼동",
                imageUriList = listOf(Uri.EMPTY)
            ),
            onEvent = {}
        )
    }
}