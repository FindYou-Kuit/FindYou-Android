package com.example.findu.presentation.ui.report.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.example.findu.R
import com.example.findu.databinding.DialogReportFinishedBinding
import com.example.findu.presentation.type.report.ReportType
import kotlinx.serialization.StringFormat

class ReportFinishDialog(
    context: Context,
    private val reportType: ReportType
) : Dialog(context) {

    private var _binding: DialogReportFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogReportFinishedBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        when (reportType) {
            ReportType.MISSING -> {
                binding.tvReportFinishDialogMention.text =
                    context.getString(R.string.report_dialog_missing_mention)
            }

            ReportType.WITNESS -> {
                binding.tvReportFinishDialogMention.text =
                    context.getString(R.string.report_dialog_witness_mention)
            }
        }

        initListener()
    }

    private fun initListener() {
        binding.btnReportFinishDialogGoHistory.setOnClickListener { }

        binding.btnReportFinishDialogGoHome.setOnClickListener { }
    }
}