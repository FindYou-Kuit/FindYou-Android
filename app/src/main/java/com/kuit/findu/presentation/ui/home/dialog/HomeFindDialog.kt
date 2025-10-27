package com.kuit.findu.presentation.ui.home.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import com.kuit.findu.R
import com.kuit.findu.databinding.DialogHomeFindBinding

class HomeFindDialog(
    context: Context,
    private val navController: NavController
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
            navigateToMissingReport()
        }

        binding.llDialogHomeFindSearch.setOnClickListener {
            navigateToSearch()
        }
    }

    private fun navigateToMissingReport() {
        navController.navigate(R.id.action_fragment_home_to_fragment_missing_report)
        dismiss()
    }

    private fun navigateToSearch() {
        navController.navigate(R.id.action_fragment_home_to_fragment_search)
        dismiss()
    }
}