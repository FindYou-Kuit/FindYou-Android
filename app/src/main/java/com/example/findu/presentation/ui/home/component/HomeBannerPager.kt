package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.findu.presentation.type.HomeBannerType
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeBannerPager(bannerList: List<HomeBannerType>, pagerState: PagerState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            count = bannerList.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Image(
                painter = painterResource(bannerList[page].imgRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
            )
            Text(
                text = "${pagerState.currentPage + 1}/${bannerList.size}",
                style = FindUTheme.typography.captionRegular11,
                color = FindUTheme.colors.white,
                modifier = Modifier
                    .padding(end = 20.dp, bottom = 10.dp)
                    .roundedBackgroundWithPadding(
                        backgroundColor = FindUTheme.colors.gray5.copy(alpha = 0.5f),
                        cornerRadius = 10.dp,
                        padding = PaddingValues(vertical = 3.dp, horizontal = 7.dp)
                    )
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

