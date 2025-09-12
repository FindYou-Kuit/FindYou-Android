package com.example.findu.presentation.ui.extra.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.findu.R
import com.example.findu.presentation.ui.base.FindUTopAppBar

@Composable
fun ExtraHomeCenterScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        FindUTopAppBar(
            title = R.string.home_extra_center,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = {}
            )
    }
}

@Preview
@Composable
private fun ExtraHomeCenterScreenPreview() {

}