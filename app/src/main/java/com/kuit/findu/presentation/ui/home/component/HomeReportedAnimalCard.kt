package com.kuit.findu.presentation.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.os.SystemClock
import android.util.Log
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.perf.FirebasePerformance
import com.kuit.findu.R
import com.kuit.findu.domain.model.ReportAnimal
import com.kuit.findu.presentation.type.AnimalStateType
import com.kuit.findu.presentation.ui.base.BaseVectorIcon
import com.kuit.findu.presentation.ui.base.SearchTagChip
import com.kuit.findu.presentation.util.extension.noRippleClickable
import com.kuit.findu.ui.theme.FindUTheme

@Composable
fun HomeReportedAnimalCard(
    animal: ReportAnimal,
    navigateToReportDetail: (ReportAnimal) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(224.dp)
            .border(width = 1.dp, color = FindUTheme.colors.gray3, shape = RoundedCornerShape(10.dp))
            .background(shape = RoundedCornerShape(10.dp), color = FindUTheme.colors.white)
            .noRippleClickable { navigateToReportDetail(animal) }
    ) {
        val context = LocalContext.current
        val imageRequest = remember(animal.thumbnailImageUrl) {
            val trace = FirebasePerformance.getInstance().newTrace("home_report_image_load")
            var startTime = 0L
            ImageRequest.Builder(context)
                .data(animal.thumbnailImageUrl)
                .listener(
                    onStart = {
                        startTime = SystemClock.elapsedRealtime()
                        trace.start()
                    },
                    onSuccess = { _, _ ->
                        val duration = SystemClock.elapsedRealtime() - startTime
                        Log.d("ImagePerf", "제보동물 이미지 로딩 완료: ${duration}ms | ${animal.thumbnailImageUrl}")
                        trace.stop()
                    },
                    onError = { _, _ ->
                        val duration = SystemClock.elapsedRealtime() - startTime
                        Log.e("ImagePerf", "제보동물 이미지 로딩 실패: ${duration}ms | ${animal.thumbnailImageUrl}")
                        trace.putAttribute("status", "error")
                        trace.stop()
                    }
                )
                .build()
        }
        AsyncImage(
            model = imageRequest,
            contentDescription = "Animal Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(224f / 133f)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
            placeholder = painterResource(R.drawable.img_findu_logo),
            error = painterResource(R.drawable.img_findu_logo)
        )
        Column(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = animal.title,
                    style = FindUTheme.typography.body1SemiBold16,
                    color = FindUTheme.colors.gray6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(5.dp))
                SearchTagChip(
                    animalStateType = AnimalStateType.fromTag(animal.tag)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                BaseVectorIcon(vectorResource = R.drawable.ic_reported_card_center_18)
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = animal.happenLocation,
                    style = FindUTheme.typography.captionRegular12,
                    color = FindUTheme.colors.gray5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                BaseVectorIcon(vectorResource = R.drawable.ic_reported_card_location_18)
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = animal.happenLocation,
                    style = FindUTheme.typography.captionRegular12,
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
private fun HomeReportedAnimalCardPreview() {
    HomeReportedAnimalCard(
        animal = ReportAnimal(
            reportId = 1,
            thumbnailImageUrl = "",
            title = "댕대래댕댕대댕댕댕댕댕애댕이",
            tag = "보호중",
            registerDate = "",
            happenLocation = "서울시 송파구"
        ),
        navigateToReportDetail = {}
    )
}