package com.example.findu.presentation.ui.report.witness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.findu.databinding.FragmentNewWitnessReportBinding
import com.example.findu.presentation.ui.report.dialog.ReportLocationActivity
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog.Companion.POST_TAG
import com.example.findu.presentation.ui.report.witness.navigation.WitnessReportNavHost
import com.example.findu.presentation.ui.report.witness.navigation.WitnessReportRoute
import com.example.findu.presentation.ui.report.witness.viewmodel.NewWitnessReportViewModel
import com.example.findu.presentation.ui.report.witness.viewmodel.WitnessReportUiEffect
import com.example.findu.presentation.ui.report.witness.viewmodel.WitnessReportUiEvent

class NewWitnessReportFragment : Fragment() {
    private var _binding: FragmentNewWitnessReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewWitnessReportViewModel>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewWitnessReportBinding.inflate(inflater, container, false)
        return binding.root
    }

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

                LaunchedEffect(viewModel.uiEffect, lifecycleOwner) {
                    viewModel.uiEffect.flowWithLifecycle(
                        lifecycle = lifecycleOwner.lifecycle
                    ).collect { sideEffect ->
                        when (sideEffect) {
                            WitnessReportUiEffect.NavigateToAddressSearch -> navigateToAddressSearch()
                            WitnessReportUiEffect.NavigateToAnimalInfo -> {
                                navController.navigate(WitnessReportRoute.AnimalInfo)
                            }

                            is WitnessReportUiEffect.ShowToast -> {
                                Toast.makeText(
                                    requireContext(), sideEffect.message, Toast.LENGTH_SHORT
                                ).show()
                            }

                            WitnessReportUiEffect.DismissKeyboard -> {
                                keyboardController?.hide()
                                focusManager.clearFocus()
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

    private fun navigateToAddressSearch() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val data = result.data?.getStringExtra(POST_TAG)
                    viewModel.handleEvent(WitnessReportUiEvent.OnAddressUpdated(data ?: "주소 찾기 실패"))
                }
            }
        val intent = Intent(context, ReportLocationActivity::class.java)
        resultLauncher.launch(intent)
    }
}