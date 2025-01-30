package com.example.findu.presentation.ui.home.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogHomeFindBinding

class HomeFindDialog(
    context: Context
) : Dialog(context) {
    private var _binding: DialogHomeFindBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DialogHomeFindBinding.inflate(LayoutInflater.from(context))
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {
        binding.ivDialogHomeFindClose.setOnClickListener {
            dismiss()
        }

        binding.llDialogHomeFindReport.setOnClickListener {

        }

        binding.llDialogHomeFindSearch.setOnClickListener {

        }
    }
}