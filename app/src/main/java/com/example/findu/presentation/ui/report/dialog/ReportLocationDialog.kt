package com.example.findu.presentation.ui.report.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogReportLocationBinding

class ReportLocationDialog(
    context: Context
) : Dialog(context) {

    private var _binding: DialogReportLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogReportLocationBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.ivReportLocationDialogLocation.setOnClickListener { }

        binding.btnReportLocationDialogSet.setOnClickListener { }
    }
}