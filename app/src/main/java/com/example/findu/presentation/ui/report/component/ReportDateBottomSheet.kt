package com.example.findu.presentation.ui.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.FindUButton
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.ui.common.WheelPicker
import com.example.findu.presentation.util.extension.isOdd
import com.example.findu.ui.theme.FindUTheme
import kotlinx.datetime.LocalDateTime
import java.time.YearMonth

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportDateBottomSheet(
    modifier: Modifier = Modifier,
    nowDate: LocalDateTime,
    sheetState: ModalBottomSheetState,
    onDateSelected: (LocalDateTime) -> Unit = { },
    hideSheet: () -> Unit = { },
) {
    var selectedYear by remember { mutableIntStateOf(nowDate.year) }
    var selectedMonth by remember { mutableIntStateOf(nowDate.monthNumber) }
    var selectedDay by remember { mutableIntStateOf(nowDate.dayOfMonth) }

    val yearList by remember { mutableStateOf((2025..2025).map { it.toString() }) }
    val monthList by remember { mutableStateOf((1..12).map { it.toString() }) }
    var dayList by remember { mutableStateOf((1..YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()).map { it.toString() }) }
    LaunchedEffect(selectedYear, selectedMonth, nowDate) {
        val maxDays = if (selectedYear == nowDate.year && selectedMonth == nowDate.monthNumber) {
            nowDate.dayOfMonth
        } else {
            YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
        }
        dayList = (1..maxDays).map { it.toString() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp,
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 22.dp)
            ) {
                ReportDateBottomSheetContent(
                    modifier = modifier,
                    hideSheet = hideSheet,
                    startMonthIndex = selectedMonth,
                    startDayIndex = selectedDay,
                    onYearSelected = { selectedYear = it },
                    onMonthSelected = {
                        selectedMonth = it
                        if (selectedDay > dayList.size && dayList.isNotEmpty()) {
                            selectedDay = dayList.size
                        }
                    },
                    onDaySelected = { selectedDay = it },
                    yearList = yearList,
                    monthList = monthList,
                    dayList = dayList,
                    onConfirmClick = {
                        val dateTime = LocalDateTime(
                            year = selectedYear,
                            monthNumber = selectedMonth,
                            dayOfMonth = selectedDay,
                            hour = 0,
                            minute = 0,
                        )
                        onDateSelected(dateTime)
                    }
                )
            }
        }
    ) { }
}

@Composable
fun ReportDateBottomSheetContent(
    modifier: Modifier = Modifier,
    hideSheet: () -> Unit = { },
    startMonthIndex: Int,
    startDayIndex: Int,
    onYearSelected: (Int) -> Unit,
    onMonthSelected: (Int) -> Unit,
    onDaySelected: (Int) -> Unit,
    yearList: List<String> = emptyList(),
    monthList: List<String> = emptyList(),
    dayList: List<String> = emptyList(),
    onConfirmClick: () -> Unit = { },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.End)
                .clip(CircleShape)
                .clickable { hideSheet() }
                .background(FindUTheme.colors.gray1)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = FindUTheme.colors.gray4,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center)
            )
        }
        VerticalSpacer(20.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.year),
                    style = FindUTheme.typography.body1SemiBold16.copy(color = FindUTheme.colors.gray5),
                )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.month),
                    style = FindUTheme.typography.body1SemiBold16.copy(color = FindUTheme.colors.gray5),
                )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.day),
                    style = FindUTheme.typography.body1SemiBold16.copy(color = FindUTheme.colors.gray5),
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(34.dp)
                    .background(
                        color = FindUTheme.colors.gray1,
                        shape = RoundedCornerShape(8.dp),
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
            ) {
                WheelPicker(
                    modifier = Modifier.weight(1f),
                    items = yearList,
                    startIndex = 0,
                    onSelected = { if (yearList.isNotEmpty()) onYearSelected(yearList[it].toInt()) }
                )

                WheelPicker(
                    modifier = Modifier.weight(1f),
                    items = monthList,
                    startIndex = if (monthList.isNotEmpty()) startMonthIndex - 1 else 0,
                    onSelected = { if (monthList.isNotEmpty()) onMonthSelected(monthList[it].toInt()) }
                )

                WheelPicker(
                    modifier = Modifier.weight(1f),
                    items = dayList,
                    startIndex = if (dayList.isNotEmpty()) startDayIndex - 1 else 0,
                    onSelected = { if (dayList.isNotEmpty()) onDaySelected(dayList[it].toInt()) }
                )
            }
        }
        FindUButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(51.dp),
            textRes = R.string.report_confirm,
            onClick = onConfirmClick,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun ReportDateBottomSheetContentPreview() {
    FindUTheme {
        ReportDateBottomSheet(
            nowDate = LocalDateTime(2024, 6, 15, 0, 0),
            sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Expanded,
                skipHalfExpanded = true,
            ),
        )
    }
}