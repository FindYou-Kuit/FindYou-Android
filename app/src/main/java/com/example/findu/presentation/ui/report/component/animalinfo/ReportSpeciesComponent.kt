package com.example.findu.presentation.ui.report.component.animalinfo

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ReportSpeciesComponent(
    modifier: Modifier = Modifier,
    selectedSpecies: SpeciesType?,
    onSpeciesClick: (SpeciesType) -> Unit,
) {
    val density = LocalDensity.current
    var widthPx by remember { mutableIntStateOf(0) }
    val segmentCount = SpeciesType.entries.size
    val (selectedBoxWidthDp, selectedBoxWidthPx) = remember(widthPx) {
        with(density) {
            Pair((widthPx / segmentCount).toDp(), (widthPx / segmentCount))
        }
    }
    val targetX = remember(selectedBoxWidthPx, selectedSpecies) {
        when (selectedSpecies) {
            SpeciesType.DOG -> 0
            SpeciesType.CAT -> selectedBoxWidthPx
            SpeciesType.ETC -> selectedBoxWidthPx * 2
            null -> 0
        }
    }

    val animatedX by animateIntAsState(
        targetValue = targetX,
        animationSpec = tween(durationMillis = 240, easing = FastOutSlowInEasing),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { widthPx = it.size.width },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.report_species_title),
                style = FindUTheme.typography.body1SemiBold16,
                color = FindUTheme.colors.gray6,
            )
            Text(
                text = stringResource(R.string.asterisk),
                style = FindUTheme.typography.body2SemiBold14.copy(color = FindUTheme.colors.red1)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(
                    color = FindUTheme.colors.gray3,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            selectedSpecies?.let {
                Box(
                    modifier = Modifier.matchParentSize()
                ) {
                    Box(
                        modifier = modifier
                            .offset { IntOffset(animatedX, 0) }
                            .fillMaxHeight()
                            .size(selectedBoxWidthDp, 44.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(
                                color = FindUTheme.colors.white,
                            )
                            .border(
                                width = 2.dp,
                                color = FindUTheme.colors.mainColor,
                                shape = RoundedCornerShape(30.dp)
                            )
                    )
                }
            }
            Row(
                modifier = modifier
                    .matchParentSize()
            ) {
                SpeciesType.entries.forEach { species ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .noRippleClickable { onSpeciesClick(species) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = species.species,
                            style = FindUTheme.typography.body1SemiBold16.copy(
                                color = if (selectedSpecies == species) FindUTheme.colors.mainColor else FindUTheme.colors.gray6
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReportSpeciesComponentPreview() {
    FindUTheme {
        ReportSpeciesComponent(
            selectedSpecies = SpeciesType.DOG,
            onSpeciesClick = {}
        )
    }
}