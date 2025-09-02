package com.example.findu.presentation.ui.report.component

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findu.R
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ReportImageComponent(
    modifier: Modifier = Modifier,
    imgUriList: List<Uri>,
    onOpenDialogClick: () -> Unit,
) {
    val density = LocalDensity.current
    var widthPx by remember { mutableIntStateOf(0) }
    val paddingDp by remember(widthPx) {
        mutableStateOf(
            with(density) {
                (widthPx.toDp() - 160.dp).coerceAtLeast(0.dp) / 2
            }
        )
    }

    Log.d("ReportImageComponent", "Recomposing ReportImageComponent with ${imgUriList.size} images")
    val pagerState = rememberPagerState(
        pageCount = { imgUriList.size + 1 }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = FindUTheme.colors.gray1,
            )
            .onGloballyPositioned { coordinates ->
                widthPx = coordinates.size.width
            }
    ) {
        VerticalSpacer(24.dp)
        Text(
            text = stringResource(R.string.report_upload_image_title),
            style = FindUTheme.typography.head2SemiBold20,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        VerticalSpacer(20.dp)
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(160.dp),
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            pageSpacing = 20.dp,
            contentPadding = PaddingValues(horizontal = paddingDp)
        ) { page ->
            Log.d("ReportImageComponent", "Rendering page $page")
            val isCurrentPage = page == pagerState.currentPage
            val itemSize by animateDpAsState(
                targetValue = if (isCurrentPage) 160.dp else 120.dp,
                animationSpec = tween(durationMillis = 300),
                label = "itemSizeAnimation"
            )

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                if (page == imgUriList.size) {
                    DefaultPageContent(
                        modifier = Modifier
                            .shadow(
                                elevation = 0.5.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(
                                color = FindUTheme.colors.gray3,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .size(itemSize)
                            .align(Alignment.Center),
                        onClick = onOpenDialogClick
                    )
                } else {
                    ImagePageContent(
                        modifier = Modifier
                            .shadow(
                                elevation = 0.5.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .size(itemSize)
                            .clip(RoundedCornerShape(20.dp))
                            .align(Alignment.Center),
                        imageUri = imgUriList[page - 1]
                    )
                }
            }
        }
        VerticalSpacer(42.dp)
    }
}

@Composable
private fun DefaultPageContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        BaseVectorIcon(
            vectorResource = R.drawable.ic_report_camera,
        )
    }
}

@Composable
private fun ImagePageContent(
    modifier: Modifier = Modifier,
    imageUri: Uri,
) {
    AsyncImage(
        modifier = modifier,
        model = imageUri,
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}