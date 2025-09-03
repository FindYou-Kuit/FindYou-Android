package com.example.findu.presentation.ui.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.findu.ui.theme.FindUTheme
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    startIndex: Int = 0,
    visibleCount: Int = 5,                // 홀수 권장 (가운데 라인 맞춤)
    itemHeight: Dp = 34.dp,
    onSelected: (index: Int) -> Unit,
) {
    val textStyle: TextStyle = FindUTheme.typography.head2SemiBold20
    val clampStartIndex = startIndex.coerceIn(0, items.lastIndex.coerceAtLeast(0))
    val state = rememberLazyListState(initialFirstVisibleItemIndex = clampStartIndex)
    val fling = rememberSnapFlingBehavior(lazyListState = state)
    val density = LocalDensity.current
    val itemHeightPx = with(density) { itemHeight.toPx() }
    val halfPadItems = (visibleCount - 1) / 2

    // 현재 "가운데"에 가장 가까운 인덱스 계산
    val currentIndex by remember(
        state,
        items
    ) {
        derivedStateOf {
            val offsetItems = state.firstVisibleItemScrollOffset / itemHeightPx
            val raw = state.firstVisibleItemIndex + offsetItems
            raw.roundToInt().coerceIn(0, items.lastIndex.coerceAtLeast(0))
        }
    }

    // 스냅 완료/정지 시 콜백
    LaunchedEffect(currentIndex) {
        onSelected(currentIndex)
    }

    Box(
        modifier = modifier
            .height(itemHeight * visibleCount)
            .clip(RoundedCornerShape(16.dp))
            .wheelFadeMask() // ← 위아래 페이드(블러 느낌)
    ) {
        // 리스트
        LazyColumn(
            state = state,
            flingBehavior = fling,
            contentPadding = PaddingValues(vertical = itemHeight * halfPadItems),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { index ->
                // 중심에서 떨어진 정도로 알파/스케일 조절
                val dist = abs(index - currentIndex).toFloat()
                val targetAlpha = 1f - (dist * 0.3f)      // 멀수록 더 투명

                val alpha by animateFloatAsState(targetValue = targetAlpha.coerceIn(0.3f, 1f))

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .graphicsLayer { this.alpha = alpha },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[index],
                        style = textStyle.copy(
                            if (index == currentIndex) FindUTheme.colors.gray6
                            else FindUTheme.colors.gray4
                        )
                    )
                }
            }
        }
    }
}

fun Modifier.wheelFadeMask(fadeDp: Dp = 36.dp): Modifier = this
    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen } // 필수
    .drawWithCache {
        val fadePx = fadeDp.toPx()
        val colors = listOf(Color.Transparent, Color.Black, Color.Black, Color.Transparent)
        val stops = listOf(0f, fadePx / size.height, 1f - fadePx / size.height, 1f)
        val colorStops = stops.zip(colors).toTypedArray()

        val mask = Brush.verticalGradient(colorStops = colorStops)
        onDrawWithContent {
            drawContent()
            drawRect(brush = mask, blendMode = BlendMode.DstIn)
        }
    }

@Preview(showBackground = true)
@Composable
private fun WheelPickerPreview() {
    WheelPicker(
        modifier = Modifier
            .padding(20.dp),
        items = List(50) { "Item #$it" },
        startIndex = 10,
        onSelected = {}
    )
}