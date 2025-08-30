package com.example.findu.presentation.ui.report.missing.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findu.presentation.ui.report.missing.screen.MissingReportScreen
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiState

@Composable
fun MissingReportNavHost(
    navController: NavHostController,
    onEvent: (MissingReportUiEvent) -> Unit,
    uiState: MissingReportUiState,
) {
    NavHost(
        navController = navController,
        startDestination = MissingReportRoute.MissingReport,
    ) {
        composable<MissingReportRoute.MissingReport> {
            MissingReportScreen(
                uiState = uiState,
                onEvent = onEvent,
            )
        }
        composable<MissingReportRoute.AnimalInfo>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            }
        ) {
            // 동물 정보 입력 스크린
        }
    }
}