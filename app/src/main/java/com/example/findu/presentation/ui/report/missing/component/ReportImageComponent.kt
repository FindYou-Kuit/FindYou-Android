package com.example.findu.presentation.ui.report.missing.component

import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findu.R
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.ui.base.VerticalSpacer
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReportImageComponent(
    modifier: Modifier = Modifier,
    imgUriList: List<Uri>,
    onOpenDialogClick: () -> Unit,
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = FindUTheme.colors.gray1,
            )
    ) {
        VerticalSpacer(24.dp)
        Text(
            text = stringResource(R.string.report_upload_image_title),
            style = FindUTheme.typography.head2SemiBold20,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        VerticalSpacer(20.dp)
        HorizontalPager(
            count = imgUriList.size + 1,
            state = pagerState,
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) { page ->
            val isCurrentPage = page == pagerState.currentPage
            val itemSize by animateDpAsState(
                targetValue = if (isCurrentPage) 160.dp else 120.dp,
                animationSpec = tween(durationMillis = 300),
                label = "itemSizeAnimation"
            )

            Box(
                modifier = Modifier
                    .size(itemSize)
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                if (page == 0) {
                    DefaultPageContent(
                        modifier = Modifier
                            .size(itemSize)
                            .background(
                                color = FindUTheme.colors.gray3,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(20.dp)
                            ),
                        onClick = onOpenDialogClick
                    )
                } else {
                    ImagePageContent(
                        modifier = Modifier
                            .size(itemSize)
                            .clip(RoundedCornerShape(20.dp))
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(20.dp)
                            ),
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