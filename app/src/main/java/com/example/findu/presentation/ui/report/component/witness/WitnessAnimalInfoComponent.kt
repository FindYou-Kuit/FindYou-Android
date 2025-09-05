package com.example.findu.presentation.ui.report.component.witness

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun WitnessAnimalInfoComponent(
    modifier: Modifier = Modifier,
    speciesType: SpeciesType?,
    breed: String?,
    onSelectAnimalInfoClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(color = FindUTheme.colors.white, shape = RoundedCornerShape(20.dp))
            .padding(vertical = 20.dp)
            .padding(start = 30.dp, end = 27.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AnimalInfoContent(
                title = R.string.report_species,
                placeholder = {
                    Text(
                        text = stringResource(R.string.report_species_placeholder),
                        style = FindUTheme.typography.body1SemiBold16,
                        color = FindUTheme.colors.gray4,
                    )
                },
                content = speciesType?.let {
                    {
                        Text(
                            text = it.species,
                            style = FindUTheme.typography.body1SemiBold16,
                            color = FindUTheme.colors.gray6,
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .size(width = 106.dp, height = 45.dp)
                    .background(
                        color = FindUTheme.colors.mainColor,
                        shape = RoundedCornerShape(30.dp)
                    )
                    .noRippleClickable { onSelectAnimalInfoClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.report_goto_info_button),
                    style = FindUTheme.typography.body2SemiBold14,
                    color = FindUTheme.colors.white,
                )
            }
        }
        AnimalInfoContent(
            title = R.string.report_breed,
            placeholder = {
                Text(
                    text = stringResource(R.string.report_breed_placeholder),
                    style = FindUTheme.typography.body1SemiBold16,
                    color = FindUTheme.colors.gray4,
                )
            },
            content = breed?.let {
                {
                    Text(
                        text = it,
                        style = FindUTheme.typography.body1SemiBold16,
                        color = FindUTheme.colors.gray6,
                    )
                }
            }
        )
    }
}

@Composable
fun AnimalInfoContent(
    @StringRes title: Int,
    placeholder: @Composable () -> Unit,
    content: @Composable (() -> Unit)? = null,
) {
    Column {
        Row {
            Text(
                text = stringResource(title),
                style = FindUTheme.typography.head3SemiBold18,
                color = FindUTheme.colors.mainColor,
            )
            Text(
                text = stringResource(R.string.asterisk),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.red1
            )
        }
        VerticalSpacer(10.dp)
        if (content == null) {
            placeholder()
        } else {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WitnessAnimalInfoComponentPreview() {
    FindUTheme {
        WitnessAnimalInfoComponent(
            speciesType = SpeciesType.CAT,
            breed = "코리안숏헤어",
            onSelectAnimalInfoClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WitnessAnimalInfoComponentNullPreview() {
    FindUTheme {
        WitnessAnimalInfoComponent(
            speciesType = null,
            breed = null,
            onSelectAnimalInfoClick = {}
        )
    }
}