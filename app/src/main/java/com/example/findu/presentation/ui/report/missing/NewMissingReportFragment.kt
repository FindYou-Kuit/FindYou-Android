package com.example.findu.presentation.ui.report.missing

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
import com.example.findu.databinding.FragmentNewMissingReportBinding
import com.example.findu.presentation.type.PermissionType
import com.example.findu.presentation.ui.report.constants.ReportConstants.IMAGE_RESULT_KEY
import com.example.findu.presentation.ui.report.constants.ReportConstants.IMAGE_URI
import com.example.findu.presentation.ui.report.dialog.ReportLocationActivity
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog.Companion.POST_TAG
import com.example.findu.presentation.ui.report.missing.navigation.MissingReportNavHost
import com.example.findu.presentation.ui.report.missing.navigation.MissingReportRoute
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEffect
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEvent
import com.example.findu.presentation.ui.report.missing.viewmodel.NewMissingReportViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class NewMissingReportFragment : Fragment() {

    private var _binding: FragmentNewMissingReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewMissingReportViewModel>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewMissingReportBinding.inflate(inflater, container, false)

        getCapturedUri()

        return binding.root
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.missingReportComposeView.apply {
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
                            viewModel.handleEvent(MissingReportUiEvent.OnImageSelected(uri))
                        } else {
                            Log.d("NewMissingReportFragment", "No media selected")
                        }
                    }
                var permissionType by remember(cameraPermissionState.status) {
                    mutableStateOf(
                        when {
                            uiState.isFirstPermissionRequest -> PermissionType.NOT_DETERMINED
                            cameraPermissionState.status.isGranted -> PermissionType.GRANTED
                            cameraPermissionState.status.shouldShowRationale -> PermissionType.SHOULD_SHOW_RATIONALE
                            else -> PermissionType.DENIED
                        }
                    )
                }
                // 권한이 승인되면 바로 카메라 이동
                LaunchedEffect(
                    cameraPermissionState.status,
                    permissionType
                ) {
                    if (
                        permissionType == PermissionType.NOT_DETERMINED || permissionType == PermissionType.SHOULD_SHOW_RATIONALE
                        && cameraPermissionState.status.isGranted
                    ) {
                        permissionType = PermissionType.GRANTED
                        viewModel.openCamera()
                    }
                }

                LaunchedEffect(viewModel.uiEffect, lifecycleOwner) {
                    viewModel.uiEffect.flowWithLifecycle(
                        lifecycle = lifecycleOwner.lifecycle
                    ).collect { sideEffect ->
                        when (sideEffect) {
                            MissingReportUiEffect.NavigateToAddressSearch -> navigateToAddressSearch()
                            MissingReportUiEffect.NavigateToAnimalInfo -> {
                                navController.navigate(MissingReportRoute.AnimalInfo)
                            }

                            is MissingReportUiEffect.ShowToast -> {
                                Toast.makeText(
                                    requireContext(), sideEffect.message, Toast.LENGTH_SHORT
                                ).show()
                            }

                            MissingReportUiEffect.DismissKeyboard -> {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }

                            MissingReportUiEffect.OpenCamera -> {
                                when (permissionType) {
                                    PermissionType.NOT_DETERMINED -> {
                                        cameraPermissionState.launchPermissionRequest()
                                    }

                                    PermissionType.DENIED -> viewModel.setAppSettingDialogVisible()
                                    PermissionType.GRANTED -> navigateToCamera()
                                    PermissionType.SHOULD_SHOW_RATIONALE -> cameraPermissionState.launchPermissionRequest()

                                }
                            }

                            MissingReportUiEffect.OpenGallery -> {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }

                            MissingReportUiEffect.OpenAppSettings -> {
                                openAppSettings()
                            }
                        }
                    }
                }

                MissingReportNavHost(
                    navController = navController,
                    onEvent = { event -> viewModel.handleEvent(event) },
                    uiState = uiState,
                )
            }
        }
    }

    private fun getCapturedUri() {
        setFragmentResultListener(IMAGE_URI) { _, result ->
            val imageUri = result.getString(IMAGE_RESULT_KEY)
            imageUri?.let {
                viewModel.handleEvent(MissingReportUiEvent.OnImageSelected(it.toUri()))
            }
            Log.d("NewWitnessReportFragment", "getCapturedUri: $imageUri")
        }
    }

    private fun navigateToAddressSearch() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val data = result.data?.getStringExtra(POST_TAG)
                    viewModel.handleEvent(MissingReportUiEvent.OnAddressUpdated(data ?: "주소 찾기 실패"))
                }
            }
        val intent = Intent(context, ReportLocationActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun navigateToCamera() {
        findNavController().navigate(
            NewMissingReportFragmentDirections.actionFragmentNewMissingReportToFragmentReportCamera()
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