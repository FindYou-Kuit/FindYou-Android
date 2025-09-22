package com.example.findu.presentation.ui.report.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findu.presentation.ui.report.screen.WitnessReportAnimalInfoScreen
import com.example.findu.presentation.ui.report.screen.WitnessReportScreen
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiState

@Composable
fun WitnessReportNavHost(
    navController: NavHostController,
    onEvent: (WitnessReportUiEvent) -> Unit,
    uiState: WitnessReportUiState,
) {
    NavHost(
        navController = navController,
        startDestination = WitnessReportRoute.WitnessReport,
    ) {
        composable<WitnessReportRoute.WitnessReport> {
            WitnessReportScreen(
                uiState = uiState,
                onEvent = onEvent,
            )
        }
        composable<WitnessReportRoute.AnimalInfo>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it })
            }
        ) {
            WitnessReportAnimalInfoScreen(
                uiState = uiState,
                onEvent = onEvent,
            )
        }
    }
}