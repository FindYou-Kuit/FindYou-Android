package com.kuit.findu.presentation.ui.base

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun BaseVectorIcon(
    @DrawableRes vectorResource: Int,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    contentDescription: String? = null
) {
    Icon(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(vectorResource),
        contentDescription = contentDescription,
        tint = tint
    )
}