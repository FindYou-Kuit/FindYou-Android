package com.example.findu.presentation.ui.home.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogHomeReportBinding

class HomeReportDialog(
    context: Context
) : Dialog(context) {
    private var _binding: DialogHomeReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogHomeReportBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.ivDialogHomeReportBack.setOnClickListener {

        }

        binding.ivDialogHomeReportClose.setOnClickListener {
            dismiss()
        }

        binding.llDialogHomeReportPhone.setOnClickListener {

        }

        binding.llDialogHomeReportWrite.setOnClickListener {

        }
    }
}