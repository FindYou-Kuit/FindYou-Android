package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeTopBar(
    reportButtonClicked:()->Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = FindUTheme.colors.white)
            .padding(top = 12.dp, start = 30.dp, bottom = 12.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.img_findu_logo),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.img_home_bell_32),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = "제보하기",
            color = FindUTheme.colors.white,
            style = FindUTheme.typography.body1SemiBold16,
            modifier = Modifier
                .border(width = 1.dp, color = FindUTheme.colors.gray2, shape = RoundedCornerShape(12.dp))
                .roundedBackgroundWithPadding(
                    backgroundColor = FindUTheme.colors.mainColor,
                    cornerRadius = 12.dp,
                    padding = PaddingValues(vertical = 10.dp, horizontal = 17.dp)
                )
                .noRippleClickable { reportButtonClicked() }
        )
    }
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    HomeTopBar(
        reportButtonClicked = {}
    )
}