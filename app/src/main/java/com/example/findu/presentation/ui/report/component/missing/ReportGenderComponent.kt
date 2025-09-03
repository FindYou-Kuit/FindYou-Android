package com.example.findu.presentation.ui.report.component.missing

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.report.Gender
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ReportGenderComponent(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
) {
    val density = LocalDensity.current
    val femalePx = remember { with(density) { 108.dp.toPx().toInt() } }
    val targetX = remember(selectedGender) {
        when (selectedGender) {
            Gender.MALE -> 0
            Gender.FEMALE -> femalePx
        }
    }

    val animatedX by animateIntAsState(
        targetValue = targetX,
        animationSpec = tween(durationMillis = 240, easing = FastOutSlowInEasing),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.report_gender_title),
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
                .size(214.dp, 44.dp)
                .background(
                    color = FindUTheme.colors.gray3,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Box(
                modifier = Modifier.matchParentSize()
            ) {
                Box(
                    modifier = modifier
                        .offset { IntOffset(animatedX, 0) }
                        .fillMaxHeight()
                        .size(106.dp, 44.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(
                            color = FindUTheme.colors.white,
                        )
                        .border(
                            width = 1.dp,
                            color = FindUTheme.colors.mainColor,
                            shape = RoundedCornerShape(30.dp)
                        )
                )
            }
            Row(
                modifier = modifier
                    .matchParentSize()
            ) {
                Gender.entries.forEach { gender ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .noRippleClickable { onGenderSelected(gender) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = gender.value,
                            style = FindUTheme.typography.body1SemiBold16.copy(
                                color = if (selectedGender == gender) FindUTheme.colors.mainColor else FindUTheme.colors.gray6
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
private fun ReportGenderComponentPreview() {
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    FindUTheme {
        ReportGenderComponent(
            selectedGender = selectedGender,
            onGenderSelected = { selectedGender = it },
        )
    }
}