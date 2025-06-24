package com.example.findu.presentation.ui.home.composeview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.domain.model.HomeReportData
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.domain.model.ReportAnimal
import com.example.findu.domain.model.ReportDataType
import com.example.findu.domain.model.ReportItem
import com.example.findu.presentation.type.HomeBannerType
import com.example.findu.presentation.ui.home.component.HomeBannerPager
import com.example.findu.presentation.ui.home.component.HomeProtectAnimalList
import com.example.findu.presentation.ui.home.component.HomeReportCard
import com.example.findu.presentation.ui.home.component.HomeReportedAnimalList
import com.example.findu.presentation.ui.home.component.HomeScrollToTopButton
import com.example.findu.presentation.ui.home.component.HomeTopBar
import com.example.findu.presentation.ui.home.component.HomeWebLinkList
import com.example.findu.presentation.ui.home.viewmodel.HomeUiState
import com.example.findu.ui.theme.FindUTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState(),
    reportButtonClicked: () -> Unit,
    homeReportData: HomeReportData,
    indicatorClicked: (String) -> Unit,
    navigationToSearch: () -> Unit,
    userNickname: String,
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp)
) {
    val bannerList = HomeBannerType.entries
    val pagerState = rememberPagerState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % bannerList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = FindUTheme.colors.white)
            .padding(innerPaddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeTopBar(reportButtonClicked = reportButtonClicked)
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = FindUTheme.colors.gray2)
            ) {
                item {
                    HomeReportCard(
                        modifier = Modifier.padding(15.dp),
                        homeReportData = homeReportData,
                        indicatorClicked = indicatorClicked
                    )
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                                color = FindUTheme.colors.white
                            )
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(R.string.home_banner_title),
                            style = FindUTheme.typography.head2SemiBold20,
                            color = FindUTheme.colors.gray6,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        HomeBannerPager(
                            bannerList = bannerList,
                            pagerState = pagerState
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = FindUTheme.colors.gray3)
                    )
                    HomeProtectAnimalList(
                        nickname = userNickname,
                        navigationToSearch = navigationToSearch,
                        animalCards = uiState.homeData!!.protectAnimalCards,
                    )
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = FindUTheme.colors.gray3)
                    )
                    HomeReportedAnimalList(
                        nickname = userNickname,
                        navigationToSearch = navigationToSearch,
                        animalCards = uiState.homeData!!.reportAnimalCards
                    )

                }
                item {
                    Box {
                        HomeWebLinkList()
                        HomeScrollToTopButton(
                            onClick = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 60.dp, end = 20.dp)
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    var selected by remember { mutableStateOf("7일") }

    val dummyProtectAnimalCards = listOf(
        ProtectAnimal(
            protectId = 1,
            thumbnailImageUrl = "",
            title = "강아지 댕댕댕댕댕댕댕이",
            tag = "보호중",
            noticeStartDate = "",
            careAddress = "서울시 강남구"
        ),
        ProtectAnimal(
            protectId = 2,
            thumbnailImageUrl = "",
            title = "고양이 야옹이",
            tag = "보호중",
            noticeStartDate = "",
            careAddress = "서울시 마포구"
        ),
        ProtectAnimal(
            protectId = 3,
            thumbnailImageUrl = "",
            title = "햄스터 하몽이",
            tag = "보호중",
            noticeStartDate = "",
            careAddress = "부산시 해운대구"
        ),
        ProtectAnimal(
            protectId = 4,
            thumbnailImageUrl = "",
            title = "토끼 깡총이",
            tag = "보호중",
            noticeStartDate = "",
            careAddress = "대구시 중구"
        ),
        ProtectAnimal(
            protectId = 5,
            thumbnailImageUrl = "",
            title = "앵무새 찡찡이",
            tag = "보호중",
            noticeStartDate = "",
            careAddress = "광주시 서구"
        ),
        ProtectAnimal(
            protectId = 6,
            thumbnailImageUrl = "",
            title = "고슴도치 도치",
            tag = "보호중",
            noticeStartDate = "",
            careAddress = "인천시 계양구"
        )
    )

    val dummyReportedAnimalCards = listOf(
        ReportAnimal(
            reportId = 1,
            thumbnailImageUrl = "",
            title = "강아지 댕댕댕댕댕댕댕이",
            tag = "보호중",
            registerDate = "",
            happenLocation = "서울시 강남구"
        ),
        ReportAnimal(
            reportId = 2,
            thumbnailImageUrl = "",
            title = "고양이 야옹이",
            tag = "보호중",
            registerDate = "",
            happenLocation = "서울시 마포구"
        ),
        ReportAnimal(
            reportId = 3,
            thumbnailImageUrl = "",
            title = "햄스터 하몽이",
            tag = "보호중",
            registerDate = "",
            happenLocation = "부산시 해운대구"
        ),
        ReportAnimal(
            reportId = 4,
            thumbnailImageUrl = "",
            title = "토끼 깡총이",
            tag = "보호중",
            registerDate = "",
            happenLocation = "대구시 중구"
        ),
        ReportAnimal(
            reportId = 5,
            thumbnailImageUrl = "",
            title = "앵무새 찡찡이",
            tag = "보호중",
            registerDate = "",
            happenLocation = "광주시 서구"
        ),
        ReportAnimal(
            reportId = 6,
            thumbnailImageUrl = "",
            title = "고슴도치 도치",
            tag = "보호중",
            registerDate = "",
            happenLocation = "인천시 계양구"
        )
    )

    val homeReportData = HomeReportData(
        reports = listOf(
            ReportItem(ReportDataType.RESCUE, 1833),
            ReportItem(ReportDataType.PROTECTION, 1744),
            ReportItem(ReportDataType.ADOPTION, 1),
            ReportItem(ReportDataType.REPORT, 6)
        )
    )
    HomeScreen(
        reportButtonClicked = {},
        homeReportData = homeReportData,
        indicatorClicked = { clickedLabel -> selected = clickedLabel },
        navigationToSearch = {},
        userNickname = "신민석"
    )
}