package com.example.findu.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun SearchTagChip(searchStatus: SearchStatus, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.roundedBackgroundWithPadding(
            backgroundColor = colorResource(searchStatus.backgroundColorRes),
            padding = PaddingValues(vertical = 2.dp, horizontal = 6.dp),
            cornerRadius = 10.dp
        )
    ) {
        Text(
            text = searchStatus.text,
            color = colorResource(searchStatus.textColor),
            style = FindUTheme.typography.tag1SemiBold12
        )
    }
}

@Preview
@Composable
private fun SearchTagChipPreview() {
    SearchTagChip(
        searchStatus = SearchStatus.PROTECTING
    )
}