package com.example.findu.presentation.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchBinding
import com.example.findu.presentation.ui.home.dialog.HomeReportDetailDialog
import com.example.findu.presentation.ui.report.dialog.ReportNavigationDialog
import com.example.findu.presentation.ui.search.adapter.SearchVPAdapter
import com.example.findu.presentation.ui.search.viewmodel.SearchViewModel
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment : Fragment() {
    companion object {
        val TAB_LIST = listOf("전체 보기", "구조 동물 조회", "신고 동물 조회")
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        initViewPager()
        return binding.root
    }

    private fun initViewPager() {
        binding.vpSearchContent.adapter = SearchVPAdapter(requireActivity())
        TabLayoutMediator(binding.tlSearchTab, binding.vpSearchContent) { tab, position ->
            tab.text = TAB_LIST[position]
            tab.contentDescription = TAB_LIST[position]
        }.attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabSearchGotoReport.setOnClickListener {
            binding.fabSearchGotoReport.visibility = View.GONE
            showDialog()
        }
    }

    private fun showCallDialog(dialog: Dialog) {
        HomeReportDetailDialog(
            requireContext(),
            dialog,
            onCloseClick = {}
        ).show()
    }

    private fun showDialog() {

        val dialog = ReportNavigationDialog(
            context = requireContext(),
            onCallClick = { showCallDialog(dialog = it) },
            onWitnessClick = ::navigateToWitnessReport,
            onMissingClick = ::navigateToMissingReport,
            onDismiss = {
                binding.fabSearchGotoReport.visibility = View.VISIBLE
            }
        )

        dialog.show()
    }

    private fun navigateToWitnessReport() {
        with(findNavController()) {
            navigate(R.id.action_fragment_search_to_fragment_witness_report)
        }
    }

    private fun navigateToMissingReport() {
        with(findNavController()) {
            navigate(R.id.action_fragment_search_to_fragment_missing_report)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}