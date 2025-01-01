package com.example.findu.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.findu.R
import com.example.findu.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupTodayData()

        return binding.root
    }

    private fun setupTodayData() {
        val rescueCount = 2 // 오늘 구조된 동물 dummy 값
        binding.tvHomeTodayRescueNum.text =
            getString(R.string.home_today_bar_rescue_num, rescueCount)

        val reportCount = 6 // 오늘 신고된 동물 dummy 값
        binding.tvHomeTodayReportNum.text =
            getString(R.string.home_today_bar_report_num, reportCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}