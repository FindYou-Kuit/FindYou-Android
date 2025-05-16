package com.example.findu.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.findu.presentation.ui.main.MainActivity
import com.example.findu.presentation.ui.onboarding.composeview.OnboardingScreen
import com.example.findu.presentation.ui.onboarding.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {
    companion object {
        private const val TAG = "Onboarding"
    }

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val uiState by onboardingViewModel.uiState.collectAsState()
            OnboardingScreen(
                uiState = uiState,
                backButtonClicked = {onboardingViewModel.onBackButtonClicked()},
                nextButtonClicked = {onboardingViewModel.onNextClicked()},
                defaultProfileClicked = {onboardingViewModel.changeDefaultProfile(it)},
            )
        }


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                onboardingViewModel.startMainActivity.collect {
                    startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

    }
}