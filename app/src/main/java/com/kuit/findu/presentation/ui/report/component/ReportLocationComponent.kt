package com.kuit.findu.presentation.ui.report.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.findu.R
import com.kuit.findu.presentation.ui.base.VerticalSpacer
import com.kuit.findu.ui.theme.FindUTheme
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ReportLocationComponent(
    modifier: Modifier = Modifier,
    address: String,
    cameraPositionState: CameraPositionState,
    nearPlace: TextFieldState,
    onAddressClick: () -> Unit = { },
    dismissKeyboard: () -> Unit = { },
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = FindUTheme.colors.mainColor,
                shape = RoundedCornerShape(24.dp),
            )
            .padding(all = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.report_location_title),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.gray6,
            )
            Text(
                text = stringResource(R.string.asterisk),
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.red1
            )
        }
        VerticalSpacer(13.dp)
        Row(
            modifier = Modifier.clickable { onAddressClick() },
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_report_location),
                contentDescription = null,
                tint = FindUTheme.colors.mainColor,
            )
            Text(
                text = address.ifEmpty { stringResource(R.string.report_location) },
                style = FindUTheme.typography.body2SemiBold14,
                color = FindUTheme.colors.gray4,
                textDecoration = TextDecoration.Underline,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
            )
        }
        VerticalSpacer(5.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentAlignment = Alignment.Center
        ) {
            NaverMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    isTiltGesturesEnabled = false,
                    isStopGesturesEnabled = false,
                    isCompassEnabled = false,
                    isLocationButtonEnabled = false,
                    isLogoClickEnabled = false,
                )
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_pin),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
        VerticalSpacer(20.dp)
        ReportInputComponent(
            titleRes = R.string.report_missing_near_location_title,
            placeHolderRes = R.string.report_missing_near_location_placeholder,
            state = nearPlace,
            isEssential = true,
            onKeyboardAction = KeyboardActionHandler { dismissKeyboard() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReportLocationComponentPreview() {
    FindUTheme {
        ReportLocationComponent(
            address = "서울특별시 강남구 테헤란로 123",
            nearPlace = TextFieldState(""),
            cameraPositionState = CameraPositionState(),
        )
    }
}