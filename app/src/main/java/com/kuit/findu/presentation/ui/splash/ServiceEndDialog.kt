package com.kuit.findu.presentation.ui.splash

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.kuit.findu.databinding.DialogServiceEndBinding

class ServiceEndDialog(
    context: Context,
    private val onConfirmClick: () -> Unit
) : Dialog(context) {

    private val binding by lazy { DialogServiceEndBinding.inflate(LayoutInflater.from(context)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        setCancelable(false)
        setCanceledOnTouchOutside(false)

        binding.btnServiceEndConfirm.setOnClickListener {
            onConfirmClick()
        }
    }
}
