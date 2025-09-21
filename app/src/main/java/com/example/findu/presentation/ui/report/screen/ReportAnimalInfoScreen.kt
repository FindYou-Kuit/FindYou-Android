package com.example.findu.presentation.ui.report.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.base.FindUButton
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.report.component.animalinfo.ReportAgeComponent
import com.example.findu.presentation.ui.report.component.animalinfo.ReportBreedComponent
import com.example.findu.presentation.ui.report.component.animalinfo.ReportSpeciesComponent
import com.example.findu.presentation.ui.report.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.MissingReportUiState
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiState
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme


@Composable
fun WitnessReportAnimalInfoScreen(
    uiState: WitnessReportUiState,
    onEvent: (WitnessReportUiEvent) -> Unit,
) {
    val buttonEnabled by remember(
        uiState.speciesType,
        uiState.breed,
    ) {
        derivedStateOf {
            uiState.speciesType != null &&
                    uiState.breed != null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable(onClick = { onEvent(WitnessReportUiEvent.ClearFocus) }),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            FindUTopAppBar(
                title = R.string.report_animal_info_title,
                navigationIconRes = R.drawable.ic_arrow_left,
                onNavigationIconClick = { onEvent(WitnessReportUiEvent.OnBackPressed) },
            )
            ReportAnimalInfoScreen(
                speciesType = uiState.speciesType,
                breed = uiState.breed,
                breedState = uiState.breedSearchText,
                breedList = uiState.breedList,
                onSpeciesClick = { onEvent(WitnessReportUiEvent.OnSpeciesClick(it)) },
                onBreedClick = { onEvent(WitnessReportUiEvent.OnBreedClick(it)) },
                clearFocus = { onEvent(WitnessReportUiEvent.ClearFocus) },
            )
        }

        FindUButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .height(50.dp)
                .padding(horizontal = 20.dp),
            textRes = R.string.report_confirm,
            onClick = { onEvent(WitnessReportUiEvent.OnInfoFinishButtonClick) },
            enabled = buttonEnabled
        )
    }
}

@Composable
fun MissingReportAnimalInfoScreen(
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {
    val buttonEnabled by remember(
        uiState.breed,
        uiState.age,
    ) {
        derivedStateOf {
            uiState.breed != null && uiState.age.text.isNotEmpty()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable(onClick = { onEvent(MissingReportUiEvent.ClearFocus) }),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            FindUTopAppBar(
                title = R.string.report_animal_info_title,
                navigationIconRes = R.drawable.ic_arrow_left,
                onNavigationIconClick = { onEvent(MissingReportUiEvent.OnBackPressed) },
            )
            ReportAnimalInfoScreen(
                speciesType = uiState.speciesType,
                breed = uiState.breed,
                breedState = uiState.breedSearchText,
                breedList = uiState.breedList,
                age = uiState.age,
                onSpeciesClick = { onEvent(MissingReportUiEvent.OnSpeciesClick(it)) },
                onBreedClick = { onEvent(MissingReportUiEvent.OnBreedClick(it)) },
                clearFocus = { onEvent(MissingReportUiEvent.ClearFocus) },
            )
        }
        FindUButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .height(50.dp)
                .padding(horizontal = 20.dp),
            textRes = R.string.report_confirm,
            onClick = { onEvent(MissingReportUiEvent.OnInfoFinishButtonClick) },
            enabled = buttonEnabled
        )
    }
}

@Composable
private fun ReportAnimalInfoScreen(
    speciesType: SpeciesType,
    breed: Breed? = null,
    breedState: TextFieldState,
    breedList: BreedData,
    age: TextFieldState? = null,
    onSpeciesClick: (SpeciesType) -> Unit = {},
    onBreedClick: (Breed) -> Unit = {},
    clearFocus: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .noRippleClickable(onClick = clearFocus),
    ) {
        ReportSpeciesComponent(
            selectedSpecies = speciesType,
            onSpeciesClick = {
                onSpeciesClick(it)
                clearFocus()
            },
        )
        VerticalSpacer(30.dp)
        ReportBreedComponent(
            breedState = breedState,
            breedList = breedList,
            selectedSpeciesType = speciesType,
            onDismissRequest = clearFocus,
            selectedBreed = breed,
            onBreedClick = onBreedClick,
        )
        VerticalSpacer(40.dp)
        age?.let { ReportAgeComponent(age = it) }
    }
}


@Preview(showBackground = true)
@Composable
private fun MissingReportAnimalInfoScreenPreview() {
    FindUTheme {
        MissingReportAnimalInfoScreen(
            uiState = MissingReportUiState(
                speciesType = SpeciesType.DOG,
                breed = Breed.DogBreed(1, "Labrador Retriever", SpeciesType.DOG),
                age = TextFieldState("3"),
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WitnessReportAnimalInfoScreenPreview() {
    FindUTheme {
        WitnessReportAnimalInfoScreen(
            uiState = WitnessReportUiState(
                speciesType = SpeciesType.DOG,
                breed = Breed.DogBreed(1, "Labrador Retriever", SpeciesType.DOG),
            ),
            onEvent = {}
        )
    }
}