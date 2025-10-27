package com.kuit.findu.presentation.ui.base

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun FindUTopAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes actionIconRes: Int? = null,
    onActionIconClick: () -> Unit = { },
    @DrawableRes navigationIconRes: Int? = null,
    onNavigationIconClick: () -> Unit = { },
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        navigationIconRes?.let {
            IconButton(
                onClick = onNavigationIconClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                BaseVectorIcon(
                    vectorResource = navigationIconRes,
                    modifier = Modifier,
                    contentDescription = "Navigation Icon",
                    tint = FindUTheme.colors.gray6
                )
            }
        }
        Text(
            text = stringResource(title),
            style = FindUTheme.typography.head3SemiBold18,
            color = FindUTheme.colors.gray6,
            modifier = Modifier.align(Alignment.Center)
        )
        actionIconRes?.let {
            IconButton(
                onClick = { onActionIconClick() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                BaseVectorIcon(
                    vectorResource = actionIconRes,
                    modifier = Modifier,
                    contentDescription = "Action Icon",
                    tint = FindUTheme.colors.black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarPreview() {
    FindUTopAppBar(
        title = R.string.app_name,
        actionIconRes = R.drawable.ic_home_top_bar_bell_24,
        onActionIconClick = {},
        navigationIconRes = R.drawable.ic_arrow_left,
        onNavigationIconClick = {}
    )
}