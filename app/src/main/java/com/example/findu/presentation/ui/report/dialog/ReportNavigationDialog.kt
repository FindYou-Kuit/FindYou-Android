package com.example.findu.presentation.ui.report.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogReportNavigationBinding

class ReportNavigationDialog(
    context: Context
) : Dialog(context) {

    private var _binding : DialogReportNavigationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogReportNavigationBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.llReportNavigationCallContainer.setOnClickListener {  }

        binding.llReportNavigationWitnessContainer.setOnClickListener {  }

        binding.llReportNavigationMissingContainer.setOnClickListener {  }
    }
}