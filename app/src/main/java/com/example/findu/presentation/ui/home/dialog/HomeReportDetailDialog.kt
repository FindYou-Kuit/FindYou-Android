package com.example.findu.presentation.ui.home.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogHomeReportDetailBinding

class HomeReportDetailDialog(
    context: Context,
    private val parentDialog: Dialog,
    private val onCloseClick: () -> Unit = {}
) : Dialog(context) {
    private var _binding: DialogHomeReportDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogHomeReportDetailBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.ivDialogHomeReportDetailBack.setOnClickListener {
            dismiss()
        }

        binding.ivDialogHomeReportDetailClose.setOnClickListener {
            parentDialog.dismiss()
            dismiss()
            onCloseClick()
        }

        binding.dialogHomeReportDetailPart.setOnClickListener {

        }

        binding.dialogHomeReportDetailCenter.setOnClickListener {

        }

        binding.dialogHomeReportDetailCallCenter.setOnClickListener {

        }
    }
}