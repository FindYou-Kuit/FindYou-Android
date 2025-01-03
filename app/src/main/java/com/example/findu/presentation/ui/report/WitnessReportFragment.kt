package com.example.findu.presentation.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.findu.R
import com.example.findu.databinding.FragmentHomeBinding
import com.example.findu.databinding.FragmentWitnessReportBinding
import com.example.findu.presentation.ui.home.HomeViewModel

class WitnessReportFragment : Fragment() {
    private var _binding: FragmentWitnessReportBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWitnessReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}