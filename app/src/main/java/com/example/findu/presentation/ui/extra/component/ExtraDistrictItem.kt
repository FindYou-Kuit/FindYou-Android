package com.example.findu.presentation.ui.extra.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findu.R
import com.example.findu.presentation.type.HomeExtraDistrictType
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme

@Composable
fun ExtraDistrictItem(
    districtType: HomeExtraDistrictType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(width = 1.dp, shape = RoundedCornerShape(30.dp), color = FindUTheme.colors.gray4)
            .roundedBackgroundWithPadding(
                backgroundColor = FindUTheme.colors.white,
                cornerRadius = 30.dp,
                padding = PaddingValues(start = 20.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
            ).noRippleClickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = districtType.stringRes),
            color = FindUTheme.colors.gray4,
            style = FindUTheme.typography.body2SemiBold14
        )
        Spacer(modifier = Modifier.weight(1f))
        BaseVectorIcon(
            vectorResource = R.drawable.ic_extra_district_dropdown_24
        )
    }
}

@Preview
@Composable
private fun ExtraDistrictItemPreview() {
    ExtraDistrictItem(
        districtType = HomeExtraDistrictType.DISTRICT_TYPE_SIDO,
        onClick = {}
    )
}