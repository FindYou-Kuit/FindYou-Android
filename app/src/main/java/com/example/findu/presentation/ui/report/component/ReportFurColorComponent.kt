package com.example.findu.presentation.ui.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.report.FurColorType
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReportFurColorComponent(
    modifier: Modifier = Modifier,
    selectedFurColors: List<FurColorType>,
    onColorSelected: (FurColorType, Boolean) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.report_color_title),
                style = FindUTheme.typography.body1SemiBold16.copy(color = FindUTheme.colors.gray6)
            )
            Text(
                text = stringResource(R.string.asterisk),
                style = FindUTheme.typography.body2SemiBold14.copy(color = FindUTheme.colors.red1)
            )
            Text(
                text = stringResource(R.string.report_color_title_additional),
                style = FindUTheme.typography.captionRegular12.copy(color = FindUTheme.colors.gray4)
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            maxItemsInEachRow = 3,
        ) {
            FurColorType.entries.forEach { furColorType ->
                FurColorChip(
                    furColorType = furColorType,
                    isSelected = selectedFurColors.contains(furColorType),
                    onClick = {
                        val isSelected = selectedFurColors.contains(furColorType)
                        onColorSelected(furColorType, !isSelected)
                    }
                )
            }
        }
    }
}

@Composable
private fun FurColorChip(
    modifier: Modifier = Modifier,
    furColorType: FurColorType,
    isSelected: Boolean,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .width(90.dp)
            .height(36.dp)
            .noRippleClickable { onClick() }
            .background(
                color = if (isSelected) FindUTheme.colors.mainColor2 else FindUTheme.colors.white,
                shape = RoundedCornerShape(30.dp),
            )
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) FindUTheme.colors.mainColor else FindUTheme.colors.gray3,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when (furColorType) {
            FurColorType.SPOTTED -> {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_spotted_color),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                )
            }

            FurColorType.OTHER -> {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_other_color),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                )
            }

            FurColorType.WHITE -> {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color(furColorType.code!!),
                            shape = CircleShape,
                        )
                        .border(
                            width = 1.dp,
                            color = FindUTheme.colors.gray3,
                            shape = CircleShape,
                        )
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color(furColorType.code!!),
                            shape = CircleShape,
                        )
                )
            }
        }
        Text(
            text = furColorType.color,
            style = if (isSelected) {
                FindUTheme.typography.body2SemiBold14.copy(color = FindUTheme.colors.gray6)
            } else {
                FindUTheme.typography.body2Regular14.copy(color = FindUTheme.colors.gray6)
            }
        )
    }
}

@Preview
@Composable
private fun FurColorChipsPreview() {
    var selectedColors by remember {
        mutableStateOf(
            listOf(
                FurColorType.BLACK,
                FurColorType.BROWN
            )
        )
    }

    FindUTheme {
        Column(
            modifier = Modifier
                .background(FindUTheme.colors.gray1)
        ) {
            ReportFurColorComponent(
                selectedFurColors = selectedColors,
                onColorSelected = { furColor, flag ->
                    selectedColors = if (flag) {
                        selectedColors.toMutableList() + furColor
                    } else {
                        selectedColors.toMutableList() - furColor
                    }
                }
            )
        }
    }

}