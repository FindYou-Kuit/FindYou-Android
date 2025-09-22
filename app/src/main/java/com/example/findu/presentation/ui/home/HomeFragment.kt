package com.example.findu.presentation.ui.home

import android.content.Intent
import android.net.Uri
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
import com.example.findu.databinding.FragmentHomeBinding
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.type.HomeExtraButtonType
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.ui.home.composeview.HomeScreen
import com.example.findu.presentation.ui.home.viewmodel.HomeUiEffect
import com.example.findu.presentation.ui.home.viewmodel.HomeUiEvent
import com.example.findu.presentation.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                            when (sideEffect) {
                                is HomeUiEffect.NavigateToProtectDetail -> {
                                    navigateToProtectDetail(id = sideEffect.animal.protectId.toString(), tag =  sideEffect.animal.tag, name =  sideEffect.animal.title)
                                }

                                is HomeUiEffect.NavigateToReportDetail -> {
                                    navigateToReportDetail(id = sideEffect.animal.reportId.toString(), tag =  sideEffect.animal.tag, name =  sideEffect.animal.title)
                                }


                                is HomeUiEffect.OpenWebLink -> {
                                    openWebLink(sideEffect.url)
                                }

                                is HomeUiEffect.ShowToast -> {
                                    Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT).show()
                                }

                                is HomeUiEffect.NavigateToProtectList -> TODO()
                                is HomeUiEffect.NavigateToReportList -> TODO()

                                is HomeUiEffect.Dial -> call120()
                            }
                        }
                }


                uiState.errorMessage?.let { message ->
                    LaunchedEffect(message) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        homeViewModel.handleEvent(HomeUiEvent.ClearError)
                    }
                }


                when (uiState.loadState) {
                    LoadState.Idle -> Unit
                    LoadState.Loading -> Unit
                    LoadState.Success -> {
                        HomeScreen(
                            uiState = uiState,
                            reportButtonClicked = {
                                homeViewModel.handleEvent(HomeUiEvent.OnReportDialogClick)
                            },
                            alarmButtonClicked = {
                                homeViewModel.handleEvent(HomeUiEvent.OnAlarmButtonClick)
                            },
                            indicatorClicked = { reportDurationType ->
                                homeViewModel.handleEvent(HomeUiEvent.OnHomeReportDurationClick(reportDurationType))
                            },
                            userNickname = "사용자",
                            navigateToProtectDetail = { protectAnimal ->
                                homeViewModel.navigateToProtectDetail(protectAnimal)
                            },
                            navigateToReportDetail = { reportAnimal ->
                                homeViewModel.navigateToReportDetail(reportAnimal)
                            },
                            navigationToProtectAnimal = {
                                homeViewModel.navigateToProtectList()
                            },
                            navigationToReportAnimal = {
                                homeViewModel.navigateToReportList()
                            },
                            onReportDialogDismiss = {
                                homeViewModel.handleEvent(HomeUiEvent.OnReportDialogDismiss)
                            },
                            onLostReportClick = {},
                            onFindReportClick = {},
                            onPhoneClicked={
                                homeViewModel.dial()
                            },
                            navigateToHomeExtra = { homeExtraButtonType->
                                navigateToHomeExtra(homeExtraButtonType) },
                        )
                    }

                    LoadState.Error -> Unit
                }
            }
        }
    }


    private fun navigateToProtectDetail(id: String, tag: String, name: String) {
        when (tag) {
            AnimalStateType.PROTECT.state -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToFragmentSearchDetailProtecting(
                        id = id,
                        tag = tag,
                        name = name
                    )
                )
            }
        }
    }

    private fun navigateToReportDetail(id: String, tag: String, name: String) {
        when (tag) {
            AnimalStateType.MISSING.state -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToFragmentSearchDetailDisappear(
                        id = id,
                        tag = tag,
                        name = name
                    )
                )
            }

            AnimalStateType.FIND.state -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToFragmentSearchDetailWitness(
                        id = id,
                        tag = tag,
                        name = name
                    )
                )
            }
        }
    }

    private fun openWebLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        requireActivity().startActivity(intent)
    }

    private fun call120() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:120"))
        startActivity(intent)
    }

    private fun navigateToHomeExtra(homeExtraButtonType: HomeExtraButtonType) {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToFragmentHomeExtra(homeExtraButtonType)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
