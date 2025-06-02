package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.domain.model.HomeReportData
import com.example.findu.domain.model.ReportDataType
import com.example.findu.domain.model.ReportItem
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.presentation.util.extension.toStringWithComma
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeReportCard(
    homeReportData: HomeReportData, indicatorClicked: (String) -> Unit, modifier: Modifier = Modifier
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
            text = "유기동물 통계", style = FindUTheme.typography.head2SemiBold20, modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        HomeReportCardIndicator(
            modifier = Modifier.padding(horizontal = 15.dp), indicatorClicked = indicatorClicked
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
    indicatorClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    selected: String = "7일",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, shape = RoundedCornerShape(10.dp), color = FindUTheme.colors.gray3
            )
            .roundedBackgroundWithPadding(
                backgroundColor = FindUTheme.colors.white, cornerRadius = 10.dp
            )
    ) {
        listOf("7일", "3개월", "1년").forEach { label ->
            val isSelected = selected == label
            val backgroundColor = if (isSelected) FindUTheme.colors.mainColor else Color.Unspecified
            val textColor = if (isSelected) FindUTheme.colors.white else FindUTheme.colors.gray5

            Text(
                text = label,
                style = FindUTheme.typography.body2SemiBold14,
                modifier = Modifier
                    .weight(1f)
                    .roundedBackgroundWithPadding(
                        backgroundColor = backgroundColor,
                        padding = PaddingValues(vertical = 8.dp),
                        cornerRadius = 10.dp
                    )
                    .noRippleClickable { indicatorClicked(label) }, // label 전달
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun HomeReportCardPreview() {
    var selected by remember { mutableStateOf("7일") }
    val homeReportData = HomeReportData(
        reports = listOf(
            ReportItem(ReportDataType.RESCUE, 1833),
            ReportItem(ReportDataType.PROTECTION, 1744),
            ReportItem(ReportDataType.ADOPTION, 1),
            ReportItem(ReportDataType.REPORT, 6)
        )
    )
    Column {
        HomeReportCard(homeReportData = homeReportData, indicatorClicked = { clickedLabel -> selected = clickedLabel })
    }
}