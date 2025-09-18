package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.findu.presentation.type.HomeExtraButtonType
import com.example.findu.presentation.type.HomeExtraType
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme


@Composable
fun HomeExtraButtonList(
    modifier: Modifier = Modifier,
    navigateToHomeExtra: (HomeExtraButtonType) -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeExtraButtonType.entries.forEach { homeButton ->
            Column(
                modifier = Modifier.noRippleClickable { navigateToHomeExtra(homeButton) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painterResource(homeButton.imageRes), contentDescription = null, tint = Color.Unspecified)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(homeButton.nameRes), style = FindUTheme.typography.body2Regular14)
            }


        }
    }
}

@Preview
@Composable
private fun HomeExtraButtonListPreview() {
    HomeExtraButtonList(modifier = Modifier.fillMaxWidth().padding(horizontal = 33.dp))
}