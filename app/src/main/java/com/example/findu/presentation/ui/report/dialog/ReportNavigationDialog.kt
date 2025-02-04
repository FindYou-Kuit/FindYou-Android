package com.example.findu.presentation.ui.report.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogReportNavigationBinding

class ReportNavigationDialog(
    context: Context,
    private val onCallClick: (Dialog) -> Unit = {},
    private val onWitnessClick: () -> Unit = {},
    private val onMissingClick: () -> Unit = {}
) : Dialog(context) {

    private val binding by lazy { DialogReportNavigationBinding.inflate(LayoutInflater.from(context)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        setCanceledOnTouchOutside(false)

        initListener()
    }

    private fun initListener() {
        binding.ivReportNavigationDialogClose.setOnClickListener {
            dismiss()
        }

        binding.llReportNavigationCallContainer.setOnClickListener {
            onCallClick(this)
        }

        binding.llReportNavigationWitnessContainer.setOnClickListener {
            onWitnessClick()
            dismiss()
        }

        binding.llReportNavigationMissingContainer.setOnClickListener {
            onMissingClick()
            dismiss()
        }
    }
}