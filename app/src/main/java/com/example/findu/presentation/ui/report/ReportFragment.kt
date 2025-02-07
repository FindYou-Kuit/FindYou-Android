package com.example.findu.presentation.ui.report

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findu.R
import com.example.findu.databinding.FragmentReportBinding
import com.example.findu.presentation.ui.home.dialog.HomeReportDetailDialog
import com.example.findu.presentation.ui.report.dialog.ReportNavigationDialog

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!
    private val reportViewModel by viewModels<ReportViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)

        showDialog()

        return binding.root
    }

    private fun showDialog() {
        val dialog = ReportNavigationDialog(
            requireContext(),
            onCallClick = { showCallDialog(it) },
            onWitnessClick = ::navigateToWitnessReport,
            onMissingClick = ::navigateToMissingReport
        )
        dialog.show()
    }

    // 추후 전화로 연결하기 다이얼로그 제작되면 그 다이얼로그를 호출함.
    private fun showCallDialog(dialog: Dialog) {
        HomeReportDetailDialog(
            requireContext(),
            dialog,
            onCloseClick = {
                findNavController().navigate(R.id.fragment_home)
            }
        ).show()
    }

    private fun navigateToWitnessReport() {
        with(findNavController()) {
            navigate(R.id.action_fragment_report_to_fragment_witness_report)
        }
    }

    private fun navigateToMissingReport() {
        with(findNavController()) {
            navigate(R.id.action_fragment_report_to_fragment_missing_report)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}