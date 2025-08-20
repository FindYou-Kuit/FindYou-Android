package com.example.findu.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun SearchTagChip(animalStateType: AnimalStateType, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.roundedBackgroundWithPadding(
            backgroundColor = colorResource(animalStateType.backgroundChipColor),
            padding = PaddingValues(vertical = 2.dp, horizontal = 6.dp),
            cornerRadius = 10.dp
        )
    ) {
        Text(
            text = animalStateType.state,
            color = colorResource(animalStateType.textColor),
            style = FindUTheme.typography.tag1SemiBold12
        )
    }
}

@Preview
@Composable
private fun SearchTagChipPreview() {
    SearchTagChip(
        animalStateType = AnimalStateType.PROTECT
    )
}