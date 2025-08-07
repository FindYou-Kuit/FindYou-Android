package com.example.findu.presentation.ui.my.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.findu.R

class MyNicknameDialog(
    context: Context,
    private val onNicknameChange: (String) -> Unit,
) {

    private val dialogView: View =
        LayoutInflater.from(context).inflate(R.layout.dialog_my_nickname_edit, null)
    private val etNickname: EditText = dialogView.findViewById(R.id.et_nickname)
    private val tvCheckDuplicate: TextView = dialogView.findViewById(R.id.tv_check_duplicate)
    private val btnChange: Button = dialogView.findViewById(R.id.btn_change_nickname)
    private val ivClose: ImageView = dialogView.findViewById(R.id.iv_dialog_close)
    private val tvState: TextView = dialogView.findViewById(R.id.tv_nickname_message)
    private val ivWarning: ImageView = dialogView.findViewById(R.id.iv_nickname_warning)
    private val llNicknameEdit: LinearLayout =
        dialogView.findViewById(R.id.ll_dialog_my_nickname_edit)

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(dialogView)
        .create()

    private var isFormatValid = false
    private var isDuplicateChecked = false

    init {
        setupListeners(context)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    }

    private fun setupListeners(context: Context) {
        ivClose.setOnClickListener { dialog.dismiss() }

        etNickname.addTextChangedListener {
            val input = it?.toString() ?: ""

            isFormatValid = input.isNotBlank()
                    && input.length <= 8
                    && !input.contains(" ")
                    && !input.contains(Regex("[^ㄱ-ㅎ가-힣a-zA-Z0-9]"))

            isDuplicateChecked = false
            llNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_default)
            tvState.text = ""
            ivWarning.visibility = View.GONE
            tvCheckDuplicate.visibility = View.VISIBLE

            updateChangeButtonState(context)
        }

        tvCheckDuplicate.setOnClickListener {
            val nickname = etNickname.text.toString()

            if (!isFormatValid) {
                llNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_fail)
                tvState.text = "사용할 수 없는 닉네임 형식이에요."
                tvState.setTextColor(ContextCompat.getColor(context, R.color.red1))
                tvCheckDuplicate.visibility = View.INVISIBLE
                ivWarning.visibility = View.VISIBLE
                return@setOnClickListener
            }
            val isDuplicated = nickname == " "

            if (isDuplicated) {
                llNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_fail)
                tvState.text = "이미 존재하는 닉네임이에요."
                tvState.setTextColor(ContextCompat.getColor(context, R.color.red1))
                ivWarning.visibility = View.VISIBLE
                isDuplicateChecked = false
            } else {
                llNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_success)
                tvState.text = "사용 가능한 닉네임이에요."
                tvState.setTextColor(ContextCompat.getColor(context, R.color.green1))
                ivWarning.visibility = View.GONE
                isDuplicateChecked = true
            }

            updateChangeButtonState(context)
        }

        btnChange.setOnClickListener {
            if(isDuplicateChecked){
                onNicknameChange(etNickname.text.toString())
                dialog.dismiss()
            }

        }
    }

    private fun updateChangeButtonState(context: Context) {
        btnChange.isEnabled = isFormatValid && isDuplicateChecked
        btnChange.setBackgroundTintList(
            ColorStateList.valueOf(
                if (btnChange.isEnabled)
                    ContextCompat.getColor(context, R.color.main_color)
                else
                    ContextCompat.getColor(context, R.color.gray2)
            )
        )
        btnChange.setTextColor(
            ContextCompat.getColor(
                context,
                if (btnChange.isEnabled) android.R.color.white else R.color.gray3
            )
        )
    }

    fun show() {
        dialog.show()
    }
}