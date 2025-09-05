package com.example.findu.presentation.ui.report.component.animalinfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.Breed
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ReportBreedComponent(
    modifier: Modifier = Modifier,
    breedState: TextFieldState,
    onDismissRequest: () -> Unit = {},
    breedList: List<Breed> = emptyList(),
    selectedBreed: Breed? = null,
    onBreedClick: (Breed) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.report_breed_title),
                style = FindUTheme.typography.body1SemiBold16,
            )
            Text(
                text = stringResource(R.string.asterisk),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.red1
            )
        }
        VerticalSpacer(10.dp)
        BasicTextField(
            state = breedState,
            textStyle = FindUTheme.typography.body2SemiBold14,
            interactionSource = interactionSource,
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = FindUTheme.colors.gray3,
                            shape = if (isFocused) RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp
                            )
                            else RoundedCornerShape(8.dp),
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.hasFocus) {
                                breedState.edit { placeCursorAtEnd() }
                            } else {
                                onDismissRequest()
                            }
                        },
                ) {
                    if (breedState.text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.report_breed_input_placeholder),
                            style = FindUTheme.typography.body2SemiBold14,
                            color = FindUTheme.colors.gray4,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                    Icon(
                        imageVector = if (isFocused) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint =
                            if (breedState.text.isEmpty()) FindUTheme.colors.gray4
                            else FindUTheme.colors.gray6,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(16.dp)
                    )
                }
            },
        )

        BreedDropdown(
            breedList = breedList,
            selectedBreed = selectedBreed,
            showDropdown = isFocused,
            onDismissRequest = onDismissRequest,
            onBreedClick = onBreedClick,
        )
    }
}

@Composable
fun BreedDropdown(
    breedList: List<Breed>,
    selectedBreed: Breed?,
    showDropdown: Boolean,
    onDismissRequest: () -> Unit = {},
    onBreedClick: (Breed) -> Unit,
) {
    AnimatedVisibility(
        visible = showDropdown && breedList.isNotEmpty(),
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = FindUTheme.colors.gray3,
                    shape = RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .clip(
                    RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                ),
        ) {
            itemsIndexed(breedList) { index, breed ->
                if (index != 0) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = FindUTheme.colors.gray3,
                    )
                }
                Text(
                    modifier = Modifier
                        .clickable {
                            onBreedClick(breed)
                            onDismissRequest()
                        }
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                    text = breed.name,
                    style = FindUTheme.typography.body2SemiBold14.copy(
                        color = if (selectedBreed == breed) FindUTheme.colors.mainColor
                        else FindUTheme.colors.gray6
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportBreedComponentPreview() {
    val focusManager = LocalFocusManager.current
    val breedState = remember { TextFieldState("") }
    var selectedBreed by remember { mutableStateOf<Breed?>(null) }
    FindUTheme {
        ReportBreedComponent(
            modifier = Modifier.padding(20.dp),
            breedState = breedState,
            breedList = listOf(
                Breed.DogBreed(1, "Labrador Retriever", SpeciesType.DOG),
                Breed.DogBreed(2, "German Shepherd", SpeciesType.DOG),
                Breed.DogBreed(3, "Golden Retriever", SpeciesType.DOG),
                Breed.DogBreed(4, "Bulldog", SpeciesType.DOG),
                Breed.DogBreed(5, "Beagle", SpeciesType.DOG),
                Breed.DogBreed(6, "Poodle", SpeciesType.DOG),
            ),
            selectedBreed = selectedBreed,
            onDismissRequest = { focusManager.clearFocus() },
            onBreedClick = {
                selectedBreed = it
                focusManager.clearFocus()
                breedState.setTextAndPlaceCursorAtEnd(it.name)
            }
        )
    }
}