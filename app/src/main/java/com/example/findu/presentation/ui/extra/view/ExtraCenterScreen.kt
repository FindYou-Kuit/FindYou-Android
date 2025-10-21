package com.example.findu.presentation.ui.extra.view

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.findu.R
import com.example.findu.domain.model.extra.Center
import com.example.findu.domain.model.extra.Sido
import com.example.findu.presentation.ui.base.BaseVectorIcon
import com.example.findu.presentation.ui.base.FindUTopAppBar
import com.example.findu.presentation.ui.extra.component.ExtraCenterItem
import com.example.findu.presentation.ui.extra.component.ExtraDistrictItem
import com.example.findu.presentation.util.extension.noRippleClickable
import com.example.findu.presentation.util.extension.roundedBackgroundWithPadding
import com.example.findu.ui.theme.FindUTheme
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch

private const val INITIAL_ZOOM_LEVEL = 14.0

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ExtraHomeCenterScreen(
    centers: List<Center>,
    selectedSido: Sido,
    selectedSigungu: String,
    sidoList: List<Sido>,
    sigunguList: List<String>,
    onSidoSelected: (Sido) -> Unit,
    onSigunguSelected: (String) -> Unit,
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit = {},
    searchCurrentLocation: (centerLatLng: LatLng) -> Unit = { }
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(latitude, longitude), INITIAL_ZOOM_LEVEL)
    }

    LaunchedEffect(latitude, longitude) {
        cameraPositionState.animate(
            com.naver.maps.map.CameraUpdate.scrollTo(LatLng(latitude, longitude))
        )
    }

    LaunchedEffect(centers) {
        if (centers.isNotEmpty()) {
            val firstCenter = centers.first()
            cameraPositionState.animate(
                CameraUpdate.scrollTo(LatLng(firstCenter.latitude, firstCenter.longitude))
            )
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        FindUTopAppBar(
            title = R.string.home_extra_center,
            navigationIconRes = R.drawable.ic_arrow_left,
            onNavigationIconClick = popBackStack,
            modifier = Modifier.background(color = FindUTheme.colors.white)
        )
        Box(modifier = Modifier.weight(1f)) {

            NaverMap(
                modifier = Modifier
                    .padding(bottom = 320.dp)
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    isCompassEnabled = false
                )
            ) {

                cameraPositionState.contentBounds?.let { visibleBounds ->
                    val visibleCenters = centers.filter { center ->
                        visibleBounds.contains(LatLng(center.latitude, center.longitude))
                    }

                    visibleCenters.forEach { center ->
                        Marker(
                            state = rememberMarkerState(position = LatLng(center.latitude, center.longitude)),
                            icon = OverlayImage.fromResource(R.drawable.ic_home_extra_volunteer_gps_20)
                        )
                    }
                }

            }

            Box(
                modifier = Modifier
                    .padding(bottom = 320.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(20.dp)
                ) {
                    ExtraDistrictItem(
                        modifier = Modifier.weight(1f),
                        selectedDistrict = selectedSido.name,
                        districtOptions = sidoList,
                        onDistrictSelected = onSidoSelected,
                        hint = stringResource(R.string.home_extra_sido),
                        itemToString = { it.name }
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    ExtraDistrictItem(
                        modifier = Modifier.weight(1f),
                        selectedDistrict = selectedSigungu,
                        districtOptions = sigunguList,
                        onDistrictSelected = onSigunguSelected,
                        hint = stringResource(R.string.home_extra_sigungu),
                        itemToString = { it }

                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 10.dp, end = 10.dp, bottom = 30.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    BaseVectorIcon(
                        vectorResource = R.drawable.ic_place_now_24,
                        modifier = Modifier
                            .roundedBackgroundWithPadding(
                                backgroundColor = FindUTheme.colors.white,
                                cornerRadius = 12.dp,
                                padding = PaddingValues(12.dp)
                            )
                            .noRippleClickable {
                                when (PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) -> {
                                        scope.launch {
                                            cameraPositionState.animate(
                                                CameraUpdate.scrollAndZoomTo(
                                                    LatLng(latitude, longitude), INITIAL_ZOOM_LEVEL
                                                )
                                            )
                                        }
                                        searchCurrentLocation(LatLng(latitude, longitude))
                                    }

                                    else -> {
                                        Toast
                                            .makeText(context, "현재 위치로 이동하려면 위치 권한이 필요합니다.", Toast.LENGTH_SHORT)
                                            .show()
                                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                    }
                                }
                            }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "현 지도에서 검색 ",
                        color = FindUTheme.colors.blue1,
                        style = FindUTheme.typography.captionRegular12,
                        modifier = Modifier
                            .roundedBackgroundWithPadding(
                                backgroundColor = FindUTheme.colors.white,
                                cornerRadius = 30.dp,
                                padding = PaddingValues(vertical = 6.dp, horizontal = 15.dp)
                            )
                            .noRippleClickable {
                                val centerLatLng = cameraPositionState.position.target
                                searchCurrentLocation(centerLatLng)
                            }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .background(
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                        color = FindUTheme.colors.white
                    )
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(width = 120.dp, height = 4.dp)
                        .background(color = Color(0xFFE5E5E7), shape = RoundedCornerShape(6.dp)),
                    thickness = 1.dp,
                    color = FindUTheme.colors.gray2
                )
                LazyColumn {
                    items(centers) {
                        ExtraCenterItem(center = it)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExtraHomeCenterScreenPreview() {
    val dummyCenters = listOf(
        Center(
            jurisdiction = listOf("서울특별시 강남구", "서울특별시 서초구"),
            centerName = "한국동물구조관리협회",
            phoneNumber = "02-764-3708",
            address = "서울특별시 강남구 삼성로 1 삼성빌딩 1층",
            latitude = 37.497942,
            longitude = 127.027683
        ),
        Center(
            jurisdiction = listOf("부산광역시 해운대구", "부산광역시 수영구"),
            centerName = "부산 유기동물 구조센터",
            phoneNumber = "051-987-6543",
            address = "부산광역시 해운대구 해운대로 123",
            latitude = 37.497942,
            longitude = 127.027683
        ),
        Center(
            jurisdiction = listOf("경기도 성남시 분당구"),
            centerName = "경기 동물보호소",
            phoneNumber = "031-555-1111",
            address = "경기도 성남시 분당구 정자동 100",
            latitude = 37.497942,
            longitude = 127.027683
        )
    )

    ExtraHomeCenterScreen(
        centers = dummyCenters,
        sidoList = emptyList(),
        sigunguList = emptyList(),
        onSidoSelected = {},
        onSigunguSelected = {},
        selectedSido = Sido(id = 0, name = ""),
        selectedSigungu = "",
        latitude = 37.0,
        longitude = 127.0,
    )
}
