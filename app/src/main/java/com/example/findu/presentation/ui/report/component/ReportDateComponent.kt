package com.example.findu.presentation.ui.report.component

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReportDateComponent(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    nowDate: String,
    selectedDate: String,
    onClick: () -> Unit,
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(titleRes),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.gray6,
            )
            Text(
                text = nowDate,
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.gray6,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = FindUTheme.colors.gray3,
                    shape = RoundedCornerShape(30.dp)
                )
                .noRippleClickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            if (selectedDate.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.report_date_placeholder),
                    style = FindUTheme.typography.body1Regular16,
                    color = FindUTheme.colors.gray4,
                )
            } else {
                Text(
                    text = selectedDate,
                    style = FindUTheme.typography.body1Regular16,
                    color = FindUTheme.colors.gray6,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportDateComponentPreview() {
    FindUTheme {
        ReportDateComponent(
            titleRes = R.string.report_missing_date_title,
            selectedDate = "",
            onClick = {},
            nowDate = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN)
            )
        )
    }
}