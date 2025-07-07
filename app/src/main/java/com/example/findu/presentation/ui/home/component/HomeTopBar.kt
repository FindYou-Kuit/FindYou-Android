package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeTopBar(
    alarmButtonClicked: () -> Unit,
    reportButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = FindUTheme.colors.white)
            .padding(top = 12.dp, start = 21.dp, bottom = 12.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.img_findu_text_logo),
            contentDescription = null,
            modifier = Modifier
                .width(72.dp)
                .noRippleClickable(alarmButtonClicked),
            tint = FindUTheme.colors.mainColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.ic_home_top_bar_bell_24),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(R.string.home_report_button_text),
            color = FindUTheme.colors.white,
            style = FindUTheme.typography.body1SemiBold16,
            modifier = Modifier
                .roundedBackgroundWithPadding(
                    backgroundColor = FindUTheme.colors.mainColor,
                    cornerRadius = 12.dp,
                    padding = PaddingValues(vertical = 10.dp, horizontal = 17.dp)
                )
                .noRippleClickable(reportButtonClicked)
        )
    }
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    HomeTopBar(
        reportButtonClicked = {},
        alarmButtonClicked = {}
    )
}