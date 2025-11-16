package com.kuit.findu.presentation.ui.report.component

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kuit.findu.R
import com.kuit.findu.presentation.type.report.ReportType
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.ui.base.FindUButton
import com.kuit.findu.presentation.ui.base.VerticalSpacer
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun ReportImageComponent(
    modifier: Modifier = Modifier,
    reportType: ReportType,
    imgUriList: List<Uri>,
    onOpenDialogClick: (Int) -> Unit = {},
    onRemoveClick: (Uri) -> Unit = {},
    onDetectionClick: (Uri) -> Unit = {},
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val pagerState = rememberPagerState(
        pageCount = { imgUriList.size + 1 }
    )
    val paddingDp = remember {
        with(density) {
            ((windowInfo.containerSize.width.toDp() - 160.dp) / 2)
                .coerceAtLeast(0.dp)
        }
    }

    val buttonEnabled by remember(imgUriList) {
        derivedStateOf {
            pagerState.currentPage != imgUriList.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = FindUTheme.colors.gray1)
    ) {
        VerticalSpacer(24.dp)
        Text(
            text = stringResource(R.string.report_upload_image_title),
            style = FindUTheme.typography.head2SemiBold20,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        VerticalSpacer(20.dp)

        ImagePagerContent(
            modifier = modifier,
            pagerState = pagerState,
            imgUriList = imgUriList,
            contentPadding = PaddingValues(horizontal = paddingDp),
            onOpenDialogClick = onOpenDialogClick,
            onRemoveClick = onRemoveClick,
        )
        when (reportType) {
            ReportType.MISSING -> {
                VerticalSpacer(42.dp)
            }

            ReportType.WITNESS -> {
                VerticalSpacer(20.dp)
                FindUButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(130.dp, 42.dp),
                    textRes = R.string.report_ai_distinction,
                    onClick = {
                        if (pagerState.currentPage < imgUriList.size) {
                            onDetectionClick(imgUriList[pagerState.currentPage])
                        }
                    },
                    enabled = buttonEnabled
                )
                VerticalSpacer(20.dp)
            }
        }
    }
}

@Composable
private fun ImagePagerContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    contentPadding: PaddingValues,
    imgUriList: List<Uri>,
    onOpenDialogClick: (Int) -> Unit,
    onRemoveClick: (Uri) -> Unit = {},
) {
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(160.dp),
        modifier = modifier
            .fillMaxWidth(),
        pageSpacing = 20.dp,
        contentPadding = contentPadding
    ) { page ->
        ImagePagerItem(
            page = page,
            pagerState = pagerState,
            imgUriList = imgUriList,
            onOpenDialogClick = onOpenDialogClick,
            onRemoveClick = onRemoveClick,
        )
    }
}

@Composable
private fun ImagePagerItem(
    page: Int,
    pagerState: PagerState,
    imgUriList: List<Uri>,
    onRemoveClick: (Uri) -> Unit,
    onOpenDialogClick: (Int) -> Unit,
) {
    val isCurrentPage = page == pagerState.currentPage
    val itemSize by animateDpAsState(
        targetValue = if (isCurrentPage) 160.dp else 120.dp,
        animationSpec = tween(durationMillis = 300),
        label = "itemSizeAnimation"
    )

    Box(
        modifier = Modifier
            .size(160.dp)
            .noRippleClickable { onOpenDialogClick(page) },
        contentAlignment = Alignment.Center
    ) {
        if (page == imgUriList.size) {
            DefaultPageContent(
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        color = FindUTheme.colors.gray3,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .size(itemSize)
                    .noRippleClickable { onOpenDialogClick(page) },
            )
        } else {
            ImagePageContent(
                modifier = Modifier
                    .shadow(
                        elevation = 2.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .size(itemSize)
                    .clip(RoundedCornerShape(20.dp))
                    .noRippleClickable { onOpenDialogClick(page + 1) },
                imageUri = imgUriList[page],
                onRemoveClick = onRemoveClick
            )
        }
    }
}

@Composable
private fun DefaultPageContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        BaseVectorIcon(
            vectorResource = R.drawable.ic_report_camera,
        )
    }
}

@Composable
private fun ImagePageContent(
    imageUri: Uri,
    modifier: Modifier = Modifier,
    onRemoveClick: (Uri) -> Unit = {},
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier.matchParentSize(),
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = FindUTheme.colors.mainColor,
                    shape = CircleShape
                )
                .background(
                    shape = CircleShape,
                    color = FindUTheme.colors.mainColor2
                )
                .size(24.dp)
                .align(Alignment.TopEnd),
            onClick = { onRemoveClick(imageUri) }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = FindUTheme.colors.mainColor,
            )
        }
    }
}