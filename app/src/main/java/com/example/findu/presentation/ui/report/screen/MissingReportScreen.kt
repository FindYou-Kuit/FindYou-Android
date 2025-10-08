package com.example.findu.presentation.ui.report.screen

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.ui.base.FindUButton
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.common.AppSettingDialog
import com.example.findu.presentation.ui.common.LoadingIndicatorDialog
import com.example.findu.presentation.ui.report.component.ReportDateBottomSheet
import com.example.findu.presentation.ui.report.component.ReportDateComponent
import com.example.findu.presentation.ui.report.component.ReportDescriptionComponent
import com.example.findu.presentation.ui.report.component.ReportFurColorComponent
import com.example.findu.presentation.ui.report.component.ReportImageComponent
import com.example.findu.presentation.ui.report.component.ReportImageDialog
import com.example.findu.presentation.ui.report.component.ReportInputComponent
import com.example.findu.presentation.ui.report.component.ReportLocationComponent
import com.example.findu.presentation.ui.report.component.missing.MissingAnimalInfoComponent
import com.example.findu.presentation.ui.report.component.missing.ReportGenderComponent
import com.example.findu.presentation.ui.report.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.MissingReportUiState
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.util.extension.toKoreanDateString
import com.example.findu.ui.theme.FindUTheme
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.rememberCameraPositionState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, FlowPreview::class)
@Composable
fun MissingReportScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        uiState.currentLatLng?.let {
            position = CameraPosition(it, 15.0)
        }
    }
    LaunchedEffect(uiState.currentLatLng) {
        uiState.currentLatLng?.let {
            cameraPositionState.position = CameraPosition(it, 15.0)
        }
    }

    LaunchedEffect(cameraPositionState) {
        var lastEmitTime = 0L

        snapshotFlow { cameraPositionState.isMoving }
            .distinctUntilChanged()
            .filter { !it }
            .collect {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastEmitTime >= 2000) {
                    val latLng = cameraPositionState.position.target
                    onEvent(MissingReportUiEvent.OnCameraTargetMoved(latLng))
                    lastEmitTime = currentTime
                }
            }
    }

    val buttonEnabled by remember(
        uiState.speciesType,
        uiState.breed,
        uiState.age,
        uiState.selectedFurColors,
        uiState.missingDate,
        uiState.address,
        uiState.imageUriList
    ) {
        derivedStateOf {
            uiState.breed != null &&
                    uiState.age.text.isNotEmpty() &&
                    uiState.selectedFurColors.isNotEmpty() &&
                    uiState.missingDate.isNotEmpty() &&
                    uiState.address.isNotEmpty() &&
                    uiState.imageUriList.isNotEmpty()
        }
    }

    val sheetState = rememberModalBottomSheetState(
        skipHalfExpanded = true,
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()

    BackHandler {
        if (sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        } else {
            onEvent(MissingReportUiEvent.OnBackPressed)
        }
    }

    MissingReportScreen(
        uiState = uiState,
        onEvent = onEvent,
        onDateClick = {
            onEvent(MissingReportUiEvent.OnDismissKeyboard)
            scope.launch { sheetState.show() }
        },
        cameraPositionState = cameraPositionState,
        buttonEnabled = buttonEnabled
    )

    if (uiState.isImageDialogShown) {
        ReportImageDialog(
            onDismissRequest = { onEvent(MissingReportUiEvent.OnDismissDialog) },
            onCameraClick = { onEvent(MissingReportUiEvent.OnOpenCameraClick) },
            onGalleryClick = { onEvent(MissingReportUiEvent.OnOpenGalleryClick) }
        )
    }
    if (uiState.isAppSettingDialogShown) {
        AppSettingDialog(
            onDismissRequest = { onEvent(MissingReportUiEvent.OnDismissDialog) },
            openAppSettings = { onEvent(MissingReportUiEvent.OnAppSettingClick) }
        )
    }

    ReportDateBottomSheet(
        nowDate = uiState.nowDate,
        sheetState = sheetState,
        onDateSelected = {
            onEvent(MissingReportUiEvent.OnDateSelected(it))
            scope.launch { sheetState.hide() }
        },
        hideSheet = {
            scope.launch { sheetState.hide() }
        },
    )

    if (uiState.loadState == LoadState.Loading) {
        LoadingIndicatorDialog()
    }
}

@Composable
private fun MissingReportScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
    onDateClick: () -> Unit = {},
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
            title = R.string.report_missing,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = { onEvent(MissingReportUiEvent.OnBackPressed) }
        )
        ReportImageComponent(
            reportType = ReportType.MISSING,
            imgUriList = uiState.imageUriList,
            onRemoveClick = { onEvent(MissingReportUiEvent.OnRemoveImageClick(it)) },
            onOpenDialogClick = { onEvent(MissingReportUiEvent.OnAddImageClick(it)) }
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
                nowDate = uiState.nowDate.toKoreanDateString(),
                onClick = onDateClick
            )
            VerticalSpacer(30.dp)
            ReportDescriptionComponent(
                descriptionState = uiState.description,
                onKeyboardAction = KeyboardActionHandler {
                    onEvent(MissingReportUiEvent.OnDismissKeyboard)
                }
            )
            VerticalSpacer(30.dp)
            ReportLocationComponent(
                address = uiState.address,
                cameraPositionState = cameraPositionState,
                nearPlace = uiState.nearPlace,
                onAddressClick = { onEvent(MissingReportUiEvent.OnAddressSearchClick) },
                dismissKeyboard = { onEvent(MissingReportUiEvent.OnDismissKeyboard) }
            )
            VerticalSpacer(30.dp)
            FindUButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                textRes = R.string.my_done,
                onClick = { onEvent(MissingReportUiEvent.OnReportFinishButtonClick) },
                enabled = buttonEnabled
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 1400)
@Composable
private fun MissingReportScreenPreview() {
    FindUTheme {
        MissingReportScreen(
            uiState = MissingReportUiState(
                speciesType = SpeciesType.DOG,
                breed = Breed.DogBreed(
                    breedId = 1,
                    breedName = "말티즈",
                    species = SpeciesType.DOG
                ),
                age = TextFieldState("3"),
                missingDate = "2023년 10월 10일 (화)",
                selectedFurColors = listOf(FurColorType.OTHER),
                address = "서울시 강남구 역삼동",
                imageUriList = listOf(Uri.EMPTY)
            ),
            onEvent = {}
        )
    }
}