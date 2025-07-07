package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.HomeReportData
import com.example.findu.domain.model.ReportDataType
import com.example.findu.domain.model.ReportItem
import com.example.findu.presentation.type.HomeReportDurationType
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.presentation.util.extension.toStringWithComma
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeReportCard(
    homeReportData: HomeReportData,
    homeReportDuration: HomeReportDurationType,
    indicatorClicked: (HomeReportDurationType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .roundedBackgroundWithPadding(
                backgroundColor = FindUTheme.colors.white,
                cornerRadius = 15.dp,
                padding = PaddingValues(vertical = 20.dp)
            )
    ) {
        Text(
            text = stringResource(R.string.home_report_card_title),
            style = FindUTheme.typography.head2SemiBold20,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        HomeReportCardIndicator(
            modifier = Modifier.padding(horizontal = 15.dp), indicatorClicked = indicatorClicked,
            selected = homeReportDuration
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            homeReportData.reports.forEachIndexed { index, data ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = data.count.toStringWithComma(),
                        style = FindUTheme.typography.head3SemiBold18,
                        color = FindUTheme.colors.gray6
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = data.type.label,
                        style = FindUTheme.typography.captionRegular12,
                        color = FindUTheme.colors.gray5
                    )
                }

                if (index < homeReportData.reports.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .width(1.dp)
                            .height(55.dp)
                            .background(FindUTheme.colors.gray3)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeReportCardIndicator(
    indicatorClicked: (HomeReportDurationType) -> Unit,
    modifier: Modifier = Modifier,
    selected: HomeReportDurationType,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .roundedBackgroundWithPadding(
                backgroundColor = FindUTheme.colors.gray1, cornerRadius = 30.dp
            )
    ) {
        HomeReportDurationType.entries.forEach { duration ->
            val isSelected = selected == duration
            val backgroundColor = if (isSelected) FindUTheme.colors.white else Color.Unspecified
            val textColor = if (isSelected) FindUTheme.colors.mainColor else FindUTheme.colors.gray6
            val textStyle =
                if (isSelected) FindUTheme.typography.body1SemiBold16 else FindUTheme.typography.body2SemiBold14


            Box(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        if (isSelected) {
                            Modifier
                                .background(backgroundColor, RoundedCornerShape(30.dp))
                                .border(1.dp, FindUTheme.colors.mainColor, RoundedCornerShape(30.dp))
                                .padding(vertical = 8.dp)
                        } else {
                            Modifier.roundedBackgroundWithPadding(
                                backgroundColor = backgroundColor,
                                padding = PaddingValues(vertical = 8.dp),
                                cornerRadius = 30.dp
                            )
                        }
                    )
                    .noRippleClickable { indicatorClicked(duration) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = duration.label,
                    style = textStyle,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview
@Composable
private fun HomeReportCardPreview() {
    var selected by remember { mutableStateOf(HomeReportDurationType.WEEK) }
    val homeReportData = HomeReportData(
        reports = listOf(
            ReportItem(ReportDataType.RESCUE, 1833),
            ReportItem(ReportDataType.PROTECTION, 1744),
            ReportItem(ReportDataType.ADOPTION, 1),
            ReportItem(ReportDataType.REPORT, 6)
        )
    )
    Column {
        HomeReportCard(
            homeReportData = homeReportData,
            indicatorClicked = { clickedLabel -> selected = clickedLabel },
            homeReportDuration = HomeReportDurationType.YEAR
        )
    }
}