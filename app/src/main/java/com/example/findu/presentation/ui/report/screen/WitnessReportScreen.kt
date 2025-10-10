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
import com.example.findu.presentation.ui.report.component.ReportLocationComponent
import com.example.findu.presentation.ui.report.component.witness.WitnessAnimalInfoComponent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiState
import com.example.findu.presentation.util.extension.toKoreanDateString
import com.example.findu.ui.theme.FindUTheme
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.rememberCameraPositionState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce

@OptIn(ExperimentalMaterialApi::class, FlowPreview::class)
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
                    onEvent(WitnessReportUiEvent.OnCameraTargetMoved(latLng))
                    lastEmitTime = currentTime
                }
            }
    }

    val buttonEnabled by remember(
        uiState.speciesType,
        uiState.breed,
        uiState.selectedFurColors,
        uiState.witnessDate,
        uiState.address,
        uiState.imageUriList,
        uiState.nearPlace
    ) {
        derivedStateOf {
            uiState.breed != null &&
                    uiState.selectedFurColors.isNotEmpty() &&
                    uiState.witnessDate.isNotEmpty() &&
                    uiState.address.isNotEmpty() &&
                    uiState.imageUriList.isNotEmpty() &&
                    uiState.nearPlace.text.isNotEmpty()
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
            onEvent(WitnessReportUiEvent.OnBackPressed)
        }
    }

    WitnessReportScreen(
        uiState = uiState,
        onEvent = onEvent,
        onDateClick = {
            onEvent(WitnessReportUiEvent.OnDismissKeyboard)
            scope.launch { sheetState.show() }
        },
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

    ReportDateBottomSheet(
        nowDate = uiState.nowDate,
        sheetState = sheetState,
        onDateSelected = {
            onEvent(WitnessReportUiEvent.OnDateSelected(it))
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
private fun WitnessReportScreen(
    uiState: WitnessReportUiState,
    onEvent: (WitnessReportUiEvent) -> Unit,
    onDateClick: () -> Unit,
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
            reportType = ReportType.WITNESS,
            imgUriList = uiState.imageUriList,
            onRemoveClick = { onEvent(WitnessReportUiEvent.OnRemoveImageClick(it)) },
            onOpenDialogClick = { onEvent(WitnessReportUiEvent.OnAddImageClick(it)) },
            onDetectionClick = { onEvent(WitnessReportUiEvent.OnAIDetectionClick(it)) }
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
                nowDate = uiState.nowDate.toKoreanDateString(),
                onClick = onDateClick
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