package com.example.findu.presentation.ui.report.missing

import android.R.attr.name
import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.findu.databinding.FragmentNewMissingReportBinding
import com.example.findu.presentation.ui.home.HomeFragmentDirections
import com.example.findu.presentation.ui.report.missing.viewmodel.MissingReportUiEffect
import com.example.findu.presentation.ui.report.missing.viewmodel.NewMissingReportViewModel

class NewMissingReportFragment : Fragment() {

    private var _binding: FragmentNewMissingReportBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewMissingReportViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewMissingReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.missingReportComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(viewModel.uiEffect, lifecycleOwner) {
                    viewModel.uiEffect.flowWithLifecycle(
                        lifecycle = lifecycleOwner.lifecycle
                    ).collect { sideEffect ->
                        when (sideEffect) {
                            MissingReportUiEffect.NavigateToAddressSearch -> {}
                            MissingReportUiEffect.NavigateToAnimalInfo -> {}
                            is MissingReportUiEffect.ShowToast -> {
                                Toast.makeText(
                                    requireContext(), sideEffect.message, Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                MissingReportScreen(
                    uiState = uiState,
                    onEvent = { event -> viewModel.handleEvent(event) },
                )
            }
        }
    }

    fun navigateToAddressSearch() {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToFragmentSearchDetailProtecting(
//                id = id,
//                tag = tag,
//                name = name
            )
        )
    }
}