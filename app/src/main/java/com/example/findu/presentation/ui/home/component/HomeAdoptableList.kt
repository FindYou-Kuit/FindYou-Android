package com.example.findu.presentation.ui.home.component

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
import com.example.findu.R
import com.example.findu.domain.model.search.SearchAnimal
import com.example.findu.domain.model.search.SearchStatus
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeAdoptableList(
    nickname: String,
    navigationToSearch: () -> Unit,
    animalCards: List<SearchAnimal>,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier
        .background(color = FindUTheme.colors.white)
        .padding(bottom = 30.dp)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .noRippleClickable(navigationToSearch)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.home_adoptable_list_title), style = FindUTheme.typography.head2SemiBold20, color = FindUTheme.colors.gray6)
                Spacer(modifier = Modifier.weight(1f))
                BaseVectorIcon(vectorResource = R.drawable.ic_report_arrow_right_14)
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.home_adoptable_list_description, nickname),
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
                key = { index -> animalCards[index].cardId },
                contentType = { "animal_card" }
            ) { index ->
                HomeAdoptableCard(
                    animal = animalCards[index]
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeAdoptableListPreview() {
    val dummyAnimalCards = listOf(
        SearchAnimal(
            cardId = 1,
            thumbnailImageUrl = "",
            title = "강아지 댕댕댕댕댕댕댕이",
            location = "서울시 강남구",
            tag = SearchStatus.PROTECTING,
            date = "",
            interest = false
        ),
        SearchAnimal(
            cardId = 2,
            thumbnailImageUrl = "",
            title = "고양이 야옹이",
            location = "서울시 마포구",
            tag = SearchStatus.PROTECTING,
            date = "",
            interest = false
        ),
        SearchAnimal(
            cardId = 3,
            thumbnailImageUrl = "",
            title = "햄스터 하몽이",
            location = "부산시 해운대구",
            tag = SearchStatus.PROTECTING,
            date = "",
            interest = false
        )
        ,
        SearchAnimal(
            cardId = 4,
            thumbnailImageUrl = "",
            title = "햄스터 하몽이",
            location = "부산시 해운대구",
            tag = SearchStatus.PROTECTING,
            date = "",
            interest = false
        )
        ,
        SearchAnimal(
            cardId = 5,
            thumbnailImageUrl = "",
            title = "햄스터 하몽이",
            location = "부산시 해운대구",
            tag = SearchStatus.PROTECTING,
            date = "",
            interest = false
        )
    )
    HomeAdoptableList(
        nickname = "신민석",
        navigationToSearch = {},
        animalCards = dummyAnimalCards,
    )
}