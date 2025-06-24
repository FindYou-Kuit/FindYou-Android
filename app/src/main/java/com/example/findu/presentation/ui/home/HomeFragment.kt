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
import com.example.findu.domain.model.HomeReportData
import com.example.findu.domain.model.ReportDataType
import com.example.findu.domain.model.ReportItem
import com.example.findu.presentation.type.view.LoadState
import com.example.findu.presentation.ui.home.composeview.HomeScreen
import com.example.findu.presentation.ui.home.dialog.HomeFindDialog
import com.example.findu.presentation.ui.home.dialog.HomeReportDialog
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
                                    navigateToProtectDetail(sideEffect.id, sideEffect.tag, sideEffect.name)
                                }

                                is HomeUiEffect.NavigateToReportDetail -> {
                                    navigateToReportDetail(sideEffect.id, sideEffect.tag, sideEffect.name)
                                }

                                is HomeUiEffect.ShowReportDialog -> {
                                    showReportDialog()
                                }

                                is HomeUiEffect.ShowFindDialog -> {
                                    showFindDialog()
                                }

                                is HomeUiEffect.OpenWebLink -> {
                                    openWebLink(sideEffect.url)
                                }

                                is HomeUiEffect.ShowToast -> {
                                    Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT).show()
                                }

                                is HomeUiEffect.ScrollToTop -> {
                                    // ScrollToTop은 HomeScreen 내부에서 처리됩니다
                                }
                            }
                        }
                }


                // 에러 메시지 처리
                uiState.errorMessage?.let { message ->
                    LaunchedEffect(message) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        homeViewModel.handleEvent(HomeUiEvent.ClearError)
                    }
                }

                // HomeReportData 생성 - 실제 데이터에 맞게 수정 필요
                val homeReportData = HomeReportData(
                    reports = listOf(
                        ReportItem(
                            type = ReportDataType.RESCUE,
                            count = uiState.homeData?.todayRescuedAnimalCount ?: 0
                        ),
                        ReportItem(
                            type = ReportDataType.REPORT,
                            count = uiState.homeData?.todayReportAnimalCount ?: 0
                        )
                    )
                )

                when (uiState.loadState) {
                    LoadState.Idle -> Unit
                    LoadState.Loading -> Unit
                    LoadState.Success -> {
                        HomeScreen(
                            uiState = uiState,
                            reportButtonClicked = {
                                homeViewModel.handleEvent(HomeUiEvent.OnReportDialogClick)
                            },
                            homeReportData = homeReportData,
                            indicatorClicked = { reportType ->
                                // ReportDataType에 따른 처리
                                when (reportType) {
                                    ReportDataType.PROTECTION.label -> {
                                        // 보호중 화면으로 이동
                                    }

                                    ReportDataType.REPORT.label -> {
                                        // 신고 화면으로 이동
                                    }
                                    // 필요한 경우 추가
                                }
                            },
                            navigationToSearch = {
                                homeViewModel.handleEvent(HomeUiEvent.OnFindDialogClick)
                            },
                            userNickname = "사용자"
                        )
                    }

                    LoadState.Error -> Unit
                }
            }
        }
    }


    private fun navigateToProtectDetail(id: String, tag: String, name: String) {
        when (tag) {
            "보호중" -> {
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
            "실종신고" -> {
                findNavController().navigate(
                    HomeFragmentDirections.actionFragmentHomeToFragmentSearchDetailDisappear(
                        id = id,
                        tag = tag,
                        name = name
                    )
                )
            }

            "목격신고" -> {
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

    private fun showReportDialog() {
        val dialog = HomeReportDialog(requireContext(), findNavController())
        dialog.show()
    }

    private fun showFindDialog() {
        val dialog = HomeFindDialog(requireContext(), findNavController())
        dialog.show()
    }

    private fun openWebLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        requireActivity().startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
