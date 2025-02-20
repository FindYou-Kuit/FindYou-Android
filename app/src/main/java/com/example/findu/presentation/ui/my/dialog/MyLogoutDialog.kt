package com.example.findu.presentation.ui.my.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.example.findu.databinding.DialogLogoutBinding
import com.example.findu.databinding.DialogMyWithdrawalBinding
import com.example.findu.presentation.ui.main.MainActivity

class MyLogoutDialog(
    context: Context,
    private val onLogoutClick: () -> Unit = {}
) : Dialog(context) {

    private val binding by lazy { DialogLogoutBinding.inflate(LayoutInflater.from(context)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)

        initListener()
    }

    private fun initListener() {

        binding.btnLogoutDialogLogout.setOnClickListener {
            onLogoutClick()
            dismiss()
        }

        binding.btnLogoutDialogCancel.setOnClickListener {
            dismiss()
        }
    }

}