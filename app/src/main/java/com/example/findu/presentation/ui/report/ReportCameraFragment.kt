package com.example.findu.presentation.ui.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findu.databinding.FragmentReportCameraBinding

class ReportCameraFragment : Fragment() {

    private var _binding: FragmentReportCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportCameraBinding.inflate(inflater, container, false)



        return binding.root
    }

}