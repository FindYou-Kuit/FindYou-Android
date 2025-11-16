package com.kuit.findu.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kuit.findu.presentation.ui.main.MainActivity
import com.kuit.findu.presentation.ui.onboarding.composeview.OnboardingScreen
import com.kuit.findu.presentation.ui.onboarding.viewmodel.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {
    companion object {
        private const val TAG = "Onboarding"
        private const val MIME_TYPE_IMAGE = "image/*"
        private const val KAKAO_ID = "kakaoId"
    }

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val kakaoId = intent.getLongExtra(KAKAO_ID, -1L)
        if (kakaoId != -1L) {
            onboardingViewModel.setKakaoId(kakaoId)
        }


        setContent {
            val uiState by onboardingViewModel.uiState.collectAsState()
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    onboardingViewModel.setProfileImage(it)
                }
            }
            OnboardingScreen(
                uiState = uiState,
                backButtonClicked = { onboardingViewModel.onBackButtonClicked() },
                nextButtonClicked = { onboardingViewModel.onNextClicked() },
                defaultProfileClicked = { profile ->
                    onboardingViewModel.changeDefaultProfile(profile)
                },
                nicknameValueChanged = { nickname ->
                    onboardingViewModel.onNicknameValueChanged(nickname)
                },
                nicknameDuplicateCheck = { onboardingViewModel.nicknameDuplicateCheck() },
                focusChanged = { onboardingViewModel.focusChanged(it) },
                cameraIconClicked = {launcher.launch(MIME_TYPE_IMAGE)},
                clearProfileImage = {onboardingViewModel.setProfileImage(null)}
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