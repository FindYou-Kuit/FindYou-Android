package com.example.findu.presentation.ui.report.missing.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.report.component.animalinfo.ReportAgeComponent
import com.example.findu.presentation.ui.report.component.animalinfo.ReportBreedComponent
import com.example.findu.presentation.ui.report.component.animalinfo.ReportSpeciesComponent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiState
import com.example.findu.ui.theme.FindUTheme

@Composable
fun MissingReportAnimalInfoScreen(
    popBackStack: () -> Unit,
    uiState: MissingReportUiState,
    onEvent: (MissingReportUiEvent) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        FindUTopAppBar(
            title = R.string.report_animal_info_title,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = { onEvent(MissingReportUiEvent.OnBackPressed) },
        )
        MissingReportAnimalInfoScreen(
            speciesType = uiState.speciesType,
            breed = uiState.breed,
            age = uiState.age,
            onSpeciesClick = { onEvent(MissingReportUiEvent.OnSpeciesClick(it)) },
            onBreedClick = { onEvent(MissingReportUiEvent.OnBreedClick(it)) },
        )
    }
}

@Composable
private fun MissingReportAnimalInfoScreen(
    speciesType: SpeciesType? = null,
    breed: Breed? = null,
    age: TextFieldState,
    onSpeciesClick: (SpeciesType) -> Unit = {},
    onBreedClick: (Breed) -> Unit = {},
    clearFocus: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp),
    ) {
        ReportSpeciesComponent(
            selectedSpecies = speciesType,
            onSpeciesClick = onSpeciesClick,
        )
        VerticalSpacer(30.dp)
        ReportBreedComponent(
            breedState = TextFieldState(breed?.name ?: ""),
            onDismissRequest = clearFocus,
            selectedBreed = breed,
            onBreedClick = onBreedClick,
        )
        VerticalSpacer(40.dp)
        ReportAgeComponent(age = age)
    }
}


@Preview(showBackground = true)
@Composable
private fun ReportAnimalInfoScreenPreview() {
    FindUTheme {
        MissingReportAnimalInfoScreen(
            clearFocus = {},
            speciesType = SpeciesType.ETC,
            breed = Breed.DogBreed(1, "Labrador", SpeciesType.DOG),
            age = TextFieldState("3"),
            onSpeciesClick = { },
            onBreedClick = { },
        )
    }
}