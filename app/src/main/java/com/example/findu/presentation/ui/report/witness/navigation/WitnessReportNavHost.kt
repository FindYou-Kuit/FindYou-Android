package com.example.findu.presentation.ui.report.witness.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findu.presentation.ui.report.missing.screen.WitnessReportAnimalInfoScreen
import com.example.findu.presentation.ui.report.witness.screen.WitnessReportScreen
import com.example.findu.presentation.ui.report.witness.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.ui.report.witness.viewmodel.WitnessReportUiState

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