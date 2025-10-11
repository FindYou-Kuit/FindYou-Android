package com.example.findu.presentation.ui.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.findu.presentation.model.HomeExtraContent
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.ui.extra.view.ExtraHomeCenterScreen
import com.example.findu.presentation.ui.extra.view.ExtraHomeDepartmentScreen
import com.example.findu.presentation.ui.extra.view.ExtraHomeVolunteerScreen
import com.example.findu.presentation.ui.extra.viewmodel.HomeExtraUiEvent
import com.example.findu.presentation.ui.extra.viewmodel.HomeExtraViewModel
import com.example.findu.presentation.ui.home.dialog.HomeFindDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeExtraFragment : Fragment() {
    private var _binding: FragmentHomeExtraBinding? = null
    private val binding get() = _binding!!
    private val homeExtraViewModel by viewModels<HomeExtraViewModel>()

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
                val uiState by homeExtraViewModel.uiState.collectAsStateWithLifecycle()
                val lifecycleOwner = LocalLifecycleOwner.current
                LaunchedEffect(homeExtraViewModel.uiEffect, lifecycleOwner) {
                    homeExtraViewModel.uiEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
                        .collect { sideEffect ->
//                            when (sideEffect) {
//
//                            }
                        }
                }

                LaunchedEffect(uiState.homeExtraButtonType) {
                    homeExtraViewModel.handleEvent(HomeExtraUiEvent.LoadData)
                }

                when (uiState.loadState) {
                    LoadState.Idle -> Unit
                    LoadState.Loading -> Unit
                    LoadState.Success -> {
                        when (val content = uiState.content) {
                            is HomeExtraContent.Volunteers -> {
                                ExtraHomeVolunteerScreen(
                                    volunteerWorks = content.list,
                                    popBackStack = { findNavController().popBackStack() })
                            }

                            is HomeExtraContent.Departments -> {
                                ExtraHomeDepartmentScreen(
                                    departments = content.list,
                                    selectedSido = uiState.selectedSido,
                                    selectedSigungu = uiState.selectedSigungu,
                                    sidoList = uiState.sidoList,
                                    sigunguList = uiState.sigunguList,
                                    onSidoSelected = {
                                        homeExtraViewModel.handleEvent(HomeExtraUiEvent.SidoSelected(it))
                                    },
                                    onSigunguSelected = {
                                        homeExtraViewModel.handleEvent(HomeExtraUiEvent.SigunguSelected(it))
                                    },
                                    popBackStack = { findNavController().popBackStack() }
                                )
                            }

                            is HomeExtraContent.Centers -> {
                                ExtraHomeCenterScreen(
                                    centers = content.list,
                                    selectedSido = uiState.selectedSido,
                                    selectedSigungu = uiState.selectedSigungu,
                                    sidoList = uiState.sidoList,
                                    sigunguList = uiState.sigunguList,
                                    onSidoSelected = {
                                        homeExtraViewModel.handleEvent(HomeExtraUiEvent.SidoSelected(it))
                                    },
                                    onSigunguSelected = {
                                        homeExtraViewModel.handleEvent(HomeExtraUiEvent.SigunguSelected(it))
                                    },
                                    popBackStack = { findNavController().popBackStack() },
                                    latitude = uiState.latitude,
                                    longitude = uiState.longitude,
                                    searchCurrentLocation = { centerLatLng ->
                                        homeExtraViewModel.handleEvent(HomeExtraUiEvent.SearchCenterFocusedLatLng(centerLatLng))
                                    }
                                )
                            }

                            is HomeExtraContent.None -> Unit
                        }
                    }

                    LoadState.Error -> Unit
                }
            }
        }
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
