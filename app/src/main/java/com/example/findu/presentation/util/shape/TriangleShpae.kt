package com.example.findu.presentation.util.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.sqrt

class TriangleShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val width = size.width
        val height = (sqrt(3.0) / 2 * width).toFloat()

        val path = Path().apply {
            moveTo(width / 2f, 0f)
            lineTo(0f, height)
            lineTo(width, height)
            close()
        }
        return Outline.Generic(path)
    }
}