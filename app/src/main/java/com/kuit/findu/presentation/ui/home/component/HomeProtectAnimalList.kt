package com.kuit.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.domain.model.ProtectAnimal
import com.kuit.findu.presentation.type.HomeUserStatusType
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun HomeProtectAnimalList(
    nickname: String,
    homeUserStatusType: HomeUserStatusType,
    navigationToSearch: () -> Unit,
    navigateToProtectDetail: (ProtectAnimal) -> Unit,
    animalCards: List<ProtectAnimal>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = FindUTheme.colors.white)
            .padding(bottom = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .noRippleClickable(navigationToSearch)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.home_adoptable_list_title),
                    style = FindUTheme.typography.head2SemiBold20,
                    color = FindUTheme.colors.gray6
                )
                Spacer(modifier = Modifier.weight(1f))
                BaseVectorIcon(vectorResource = R.drawable.ic_report_arrow_right_14)
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (homeUserStatusType == HomeUserStatusType.MEMBER){
                    stringResource(homeUserStatusType.protectAnimalListTitleRes, nickname)
                }else{
                    stringResource(homeUserStatusType.protectAnimalListTitleRes)
                },
                style = FindUTheme.typography.body2Regular14, color = FindUTheme.colors.gray4
            )
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                count = animalCards.size,
                key = { index -> animalCards[index].protectId },
                contentType = { "animal_card" }
            ) { index ->
                HomeProtectAnimalCard(
                    animal = animalCards[index],
                    navigateToProtectDetail = navigateToProtectDetail
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeProtectAnimalListPreview() {
    val dummyAnimalCards = listOf(
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
    HomeProtectAnimalList(
        nickname = "신민석",
        navigationToSearch = {},
        animalCards = dummyAnimalCards,
        navigateToProtectDetail = {},
        homeUserStatusType = HomeUserStatusType.MEMBER,
    )
}