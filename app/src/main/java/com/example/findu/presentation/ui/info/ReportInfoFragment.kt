package com.example.findu.presentation.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findu.R
import com.example.findu.databinding.FragmentReportInfoBinding

class ReportInfoFragment : Fragment() {
    private var _binding: FragmentReportInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportInfoBinding.inflate(inflater, container, false)
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.ivReportInfoBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.llSecondCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            startActivity(intent)
        }

        binding.llFourthReport.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_report_info_to_fragment_search)
        }

        binding.llFourthSystem.setOnClickListener {
            val url = "https://www.animal.go.kr/front/awtis/public/publicList.do?menuNo=1000000055"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        binding.llFifthUp.setOnClickListener {
            binding.svReportInfo.smoothScrollTo(0, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}