package com.example.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.findu.R
import com.example.findu.domain.model.ProtectAnimal
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.ui.base.SearchTagChip
import com.example.findu.ui.theme.FindUTheme

@Composable
fun HomeProtectAnimalCard(
    animal: ProtectAnimal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(120.dp)
            .border(width = 1.dp, color = FindUTheme.colors.gray3, shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FindUTheme.colors.white)
    ) {
        Box(
            modifier = Modifier.size(height = 100.dp, width = 120.dp),
        ) {
            AsyncImage(
                model = animal.thumbnailImageUrl,
                contentDescription = "Animal Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                placeholder = painterResource(R.drawable.img_findu_logo),
                error = painterResource(R.drawable.img_findu_logo)
            )
            SearchTagChip(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(5.dp), animalStateType = AnimalStateType.fromTag(animal.tag)
            )
        }
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp)) {
            Text(
                text = animal.title,
                style = FindUTheme.typography.tag1SemiBold12,
                color = FindUTheme.colors.gray6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                BaseVectorIcon(R.drawable.icon_home_location)
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = animal.careAddress,
                    style = FindUTheme.typography.homeSemiBold10,
                    color = FindUTheme.colors.gray5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeProtectAnimalCardPreview() {
    HomeProtectAnimalCard(
        animal = ProtectAnimal(
            thumbnailImageUrl = "",
            title = "콩이",
            tag = "보호중",
            protectId = 1,
            noticeStartDate = "",
            careAddress = "서울시 송파구"
        )
    )
}