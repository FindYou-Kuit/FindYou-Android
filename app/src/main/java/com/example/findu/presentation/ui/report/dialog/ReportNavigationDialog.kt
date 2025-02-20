package com.example.findu.presentation.ui.report.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.example.findu.databinding.DialogReportNavigationBinding

class ReportNavigationDialog(
    context: Context,
    private val onCallClick: (Dialog) -> Unit = {},
    private val onWitnessClick: () -> Unit = {},
    private val onMissingClick: () -> Unit = {},
    private val onDismiss: () -> Unit = {}
) : Dialog(context) {

    private val binding by lazy { DialogReportNavigationBinding.inflate(LayoutInflater.from(context)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.dialogReportNavigationMainContainer.setBackgroundColor(Color.TRANSPARENT)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.END or Gravity.BOTTOM)

        setContentView(binding.root)

        initListener()

        this.setOnCancelListener {
            onDismiss()
        }

    }

    private fun initListener() {

        binding.clReportNavigationCloseContainer.setOnClickListener {
            dismiss()
            onDismiss()
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