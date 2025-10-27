package com.kuit.findu.ui.theme

import android.app.Activity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object FindUTheme {
    val colors: FindUColors
        @Composable
        @ReadOnlyComposable
        get() = LocalFindUColors.current

    val typography: FindUTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalFindUTypography.current
}

@Composable
fun ProvideFindUColorsAndTypography(
    colors: FindUColors,
    typography: FindUTypography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalFindUColors provides colors,
        LocalFindUTypography provides typography,
        content = content
    )
}

@Composable
fun FindUTheme(
    backgroundColor: Color = defaultFindUColors.white,
    content: @Composable () -> Unit
) {
    ProvideFindUColorsAndTypography(colors = defaultFindUColors, typography = defaultFindUTypography) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.run {
                    statusBarColor = backgroundColor.toArgb()
                    WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars = true
                }
            }
        }

        MaterialTheme(
            content = content
        )
    }
}
