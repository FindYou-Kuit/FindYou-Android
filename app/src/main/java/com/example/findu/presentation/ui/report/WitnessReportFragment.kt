package com.example.findu.presentation.ui.report

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.example.findu.databinding.FragmentWitnessReportBinding
import com.example.findu.presentation.type.PermissionType
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.constants.ReportConstants
import com.example.findu.presentation.ui.report.dialog.ReportFinishDialog
import com.example.findu.presentation.ui.report.dialog.ReportLocationActivity
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog
import com.example.findu.presentation.ui.report.navigation.WitnessReportNavHost
import com.example.findu.presentation.ui.report.navigation.WitnessReportRoute
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEffect
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportUiEvent
import com.example.findu.presentation.ui.report.viewmodel.WitnessReportViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class WitnessReportFragment : Fragment() {
    private var _binding: FragmentWitnessReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WitnessReportViewModel>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWitnessReportBinding.inflate(inflater, container, false)

        getCapturedUri()

        return binding.root
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.witnessReportComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val lifecycleOwner = LocalLifecycleOwner.current
                val navController = rememberNavController()
                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                val pickMedia =
                    rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                        if (uri != null) {
                            viewModel.handleEvent(WitnessReportUiEvent.OnImageSelected(uri))
                        } else {
                            Log.d("NewMissingReportFragment", "No media selected")
                        }
                    }
                var permissionType by remember {
                    mutableStateOf(
                        when {
                            uiState.isFirstPermissionRequest -> PermissionType.NOT_DETERMINED
                            cameraPermissionState.status.isGranted -> PermissionType.GRANTED
                            cameraPermissionState.status.shouldShowRationale -> PermissionType.SHOULD_SHOW_RATIONALE
                            else -> PermissionType.DENIED
                        }
                    )
                }
                var openCamera by remember { mutableStateOf(false) }

                // 권한이 승인되면 바로 카메라 이동
                LaunchedEffect(
                    cameraPermissionState.status,
                    permissionType
                ) {
                    if (cameraPermissionState.status.isGranted) {
                        permissionType = PermissionType.GRANTED
                        if (openCamera) {
                            viewModel.openCamera()
                            openCamera = false
                        }
                    }
                }

                LaunchedEffect(viewModel.uiEffect, lifecycleOwner) {
                    viewModel.uiEffect.flowWithLifecycle(
                        lifecycle = lifecycleOwner.lifecycle
                    ).collect { sideEffect ->
                        when (sideEffect) {
                            WitnessReportUiEffect.NavigateToUp -> {
                                val hasBackStack = navController.previousBackStackEntry != null
                                if (hasBackStack) {
                                    // 정보 입력 뒤로가기
                                    navController.popBackStack()
                                } else {
                                    // 신고화면에서 뒤로가기
                                    findNavController().popBackStack()
                                }
                            }

                            WitnessReportUiEffect.NavigateToAddressSearch -> navigateToAddressSearch()
                            WitnessReportUiEffect.NavigateToAnimalInfo -> {
                                navController.navigate(WitnessReportRoute.AnimalInfo)
                            }

                            is WitnessReportUiEffect.ShowToast -> {
                                Toast.makeText(
                                    requireContext(), sideEffect.message, Toast.LENGTH_SHORT
                                ).show()
                            }

                            WitnessReportUiEffect.ShowFinishDialog -> {
                                ReportFinishDialog(
                                    requireContext(),
                                    ReportType.MISSING,
                                    onGoHistoryClick = ::navigateToReportHistory,
                                    onGoHomeClick = ::navigateToHome
                                ).show()
                            }

                            WitnessReportUiEffect.DismissKeyboard -> {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }

                            WitnessReportUiEffect.OpenCamera -> {
                                when (permissionType) {
                                    PermissionType.NOT_DETERMINED -> {
                                        cameraPermissionState.launchPermissionRequest()
                                    }

                                    PermissionType.DENIED -> viewModel.setAppSettingDialogVisible()
                                    PermissionType.GRANTED -> navigateToCamera()
                                    PermissionType.SHOULD_SHOW_RATIONALE -> cameraPermissionState.launchPermissionRequest()

                                }
                            }

                            WitnessReportUiEffect.OpenGallery -> {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }

                            WitnessReportUiEffect.OpenAppSettings -> {
                                openAppSettings()
                            }

                        }
                    }
                }

                WitnessReportNavHost(
                    navController = navController,
                    onEvent = { event -> viewModel.handleEvent(event) },
                    uiState = uiState,
                )
            }
        }
    }

    private fun getCapturedUri() {
        setFragmentResultListener(ReportConstants.IMAGE_URI) { _, result ->
            val imageUri = result.getString(ReportConstants.IMAGE_RESULT_KEY)
            imageUri?.let {
                viewModel.handleEvent(WitnessReportUiEvent.OnImageSelected(it.toUri()))
            }
        }
    }


    private fun navigateToAddressSearch() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val data = result.data?.getStringExtra(ReportLocationDialog.Companion.POST_TAG)
                    viewModel.handleEvent(WitnessReportUiEvent.OnAddressUpdated(data ?: "주소 찾기 실패"))
                }
            }
        val intent = Intent(context, ReportLocationActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun navigateToCamera() {
        findNavController().navigate(
            WitnessReportFragmentDirections.actionFragmentWitnessReportToFragmentReportCamera()
        )
    }

    private fun navigateToReportHistory() {
        findNavController().navigate(
            WitnessReportFragmentDirections.actionFragmentWitnessReportToFragmentMyReportHistory()
        )
    }

    private fun navigateToHome() {
        findNavController().navigate(
            WitnessReportFragmentDirections.actionFragmentWitnessReportToFragmentHome()
        )
    }

    private fun openAppSettings() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", requireContext().packageName, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "설정을 열 수 없습니다", Toast.LENGTH_SHORT).show()
        }
    }
}