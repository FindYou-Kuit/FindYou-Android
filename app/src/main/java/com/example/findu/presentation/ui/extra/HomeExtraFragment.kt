package com.example.findu.presentation.ui.extra

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
import com.example.findu.databinding.FragmentHomeExtraBinding
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.ui.extra.view.ExtraHomeVolunteerScreen
import com.example.findu.presentation.ui.extra.viewmodel.HomeExtraViewModel
import com.example.findu.presentation.ui.home.composeview.HomeScreen
import com.example.findu.presentation.ui.home.dialog.HomeFindDialog
import com.example.findu.presentation.ui.home.dialog.HomeReportDialog
import com.example.findu.presentation.ui.home.viewmodel.HomeUiEffect
import com.example.findu.presentation.ui.home.viewmodel.HomeUiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExtraFragment : Fragment() {
    private var _binding: FragmentHomeExtraBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeExtraViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeExtraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(homeViewModel.uiEffect, lifecycleOwner) {
                    homeViewModel.uiEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
                        .collect { sideEffect ->
//                            when (sideEffect) {
//
//                            }
                        }
                }


//                uiState.errorMessage?.let { message ->
//                    LaunchedEffect(message) {
//                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                        homeViewModel.handleEvent(HomeUiEvent.ClearError)
//                    }
//                }


                when (uiState.loadState) {
                    LoadState.Idle -> Unit
                    LoadState.Loading -> Unit
                    LoadState.Success -> {
                        ExtraHomeVolunteerScreen(
                            volunteerWorks = uiState.data
                        )
                    }

                    LoadState.Error -> Unit
                }
            }
        }
    }

    
    

    private fun showReportDialog() {
        val dialog = HomeReportDialog(requireContext(), findNavController())
        dialog.show()
    }

    private fun showFindDialog() {
        val dialog = HomeFindDialog(requireContext(), findNavController())
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
