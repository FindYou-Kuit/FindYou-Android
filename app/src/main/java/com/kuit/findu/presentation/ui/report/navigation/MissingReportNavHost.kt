package com.kuit.findu.presentation.ui.report.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kuit.findu.presentation.ui.report.screen.MissingReportAnimalInfoScreen
import com.kuit.findu.presentation.ui.report.screen.MissingReportScreen
import com.kuit.findu.presentation.ui.report.viewmodel.MissingReportUiEvent
import com.kuit.findu.presentation.ui.report.viewmodel.MissingReportUiState

@Composable
fun MissingReportNavHost(
    navController: NavHostController,
    onEvent: (MissingReportUiEvent) -> Unit,
    uiState: MissingReportUiState,
) {
    NavHost(
        navController = navController,
        startDestination = MissingReportRoute.MissingReport,
        enterTransition = { fadeIn(tween(0)) },
        exitTransition = { fadeOut(tween(0)) },
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
        ) {
            MissingReportAnimalInfoScreen(
                uiState = uiState,
                onEvent = onEvent,
            )
        }
    }
}