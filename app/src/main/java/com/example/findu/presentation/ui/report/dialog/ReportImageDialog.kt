package com.example.findu.presentation.ui.report.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogReportImageBinding

class ReportImageDialog(
    context: Context,
    private val onCapture: () -> Unit,
    private val onUpload: () -> Unit
) : Dialog(context) {

    private val binding by lazy { DialogReportImageBinding.inflate(LayoutInflater.from(context)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.ivReportImageDialogClose.setOnClickListener {
            dismiss()
        }

        binding.btnReportImageDialogCapture.setOnClickListener {
            onCapture()
            dismiss()
        }

        binding.btnReportImageDialogUpload.setOnClickListener {

            dismiss()
        }
    }
}