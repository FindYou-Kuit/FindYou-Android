package com.kuit.findu.presentation.ui.my.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.kuit.findu.R
import com.kuit.findu.databinding.DialogMyNicknameEditBinding

class MyNicknameDialog(
    context: Context,
    private val onNicknameChange: (String) -> Unit,
) {

    private val binding: DialogMyNicknameEditBinding =
        DialogMyNicknameEditBinding.inflate(LayoutInflater.from(context))

    private val dialog: AlertDialog = AlertDialog.Builder(context)
        .setView(binding.root)
        .create()

    private var errorPopup: PopupWindow? = null

    private var isFormatValid = false
    private var isDuplicateChecked = false

    init {
        setupListeners(context)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupListeners(context: Context) = with(binding) {
        ivDialogClose.setOnClickListener { dialog.dismiss() }

        etNickname.addTextChangedListener {
            val input = it?.toString() ?: ""

            isFormatValid = input.isNotBlank()
                    && input.length <= 8
                    && !input.contains(" ")
                    && !input.contains(Regex("[^ㄱ-ㅎ가-힣a-zA-Z0-9]"))

            isDuplicateChecked = false
            llDialogMyNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_default)
            tvNicknameMessage.text = ""
            ivNicknameWarning.visibility = View.GONE
            tvCheckDuplicate.visibility = View.VISIBLE

            updateChangeButtonState(context)
        }

        tvCheckDuplicate.setOnClickListener {
            val nickname = etNickname.text.toString()

            if (!isFormatValid) {
                llDialogMyNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_fail)
                tvNicknameMessage.text = "사용할 수 없는 닉네임 형식이에요."
                tvNicknameMessage.setTextColor(ContextCompat.getColor(context, R.color.red1))
                tvCheckDuplicate.visibility = View.INVISIBLE
                ivNicknameWarning.visibility = View.VISIBLE
                return@setOnClickListener
            }
            val isDuplicated = nickname == " "

            if (isDuplicated) {
                llDialogMyNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_fail)
                tvNicknameMessage.text = "이미 존재하는 닉네임이에요."
                tvNicknameMessage.setTextColor(ContextCompat.getColor(context, R.color.red1))
                ivNicknameWarning.visibility = View.VISIBLE
                isDuplicateChecked = false
            } else {
                llDialogMyNicknameEdit.setBackgroundResource(R.drawable.bg_nickname_edittext_success)
                tvNicknameMessage.text = "사용 가능한 닉네임이에요."
                tvNicknameMessage.setTextColor(ContextCompat.getColor(context, R.color.green1))
                ivNicknameWarning.visibility = View.GONE
                isDuplicateChecked = true
                tvCheckDuplicate.visibility = View.GONE
            }

            updateChangeButtonState(context)
        }

        ivNicknameWarning.setOnClickListener {
            if (errorPopup?.isShowing == true) {
                errorPopup?.dismiss()
            } else {
                showErrorPopup(context, ivNicknameWarning)
            }
        }

        btnChangeNickname.setOnClickListener {
            if (isDuplicateChecked) {
                onNicknameChange(etNickname.text.toString())
                dialog.dismiss()
            }

        }
    }

    private fun updateChangeButtonState(context: Context) = with(binding) {
        btnChangeNickname.isEnabled = isFormatValid && isDuplicateChecked
        btnChangeNickname.setBackgroundTintList(
            ColorStateList.valueOf(
                if (btnChangeNickname.isEnabled)
                    ContextCompat.getColor(context, R.color.main_color)
                else
                    ContextCompat.getColor(context, R.color.gray2)
            )
        )
        btnChangeNickname.setTextColor(
            ContextCompat.getColor(
                context,
                if (btnChangeNickname.isEnabled) android.R.color.white else R.color.gray3
            )
        )
    }

    private fun showErrorPopup(context: Context, anchorView: View) {
        val popupView = LayoutInflater.from(context)
            .inflate(R.layout.item_my_nickname_error, null)

        errorPopup = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        popupView.measure(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
        )
        val popupW = popupView.measuredWidth
        val anchorW = anchorView.width

        val xOffset = -(popupW / 2) + (anchorW / 2)

        val yOffset = -dpToPx(2, context)

        errorPopup?.showAsDropDown(anchorView, xOffset, yOffset)
    }

    private fun dpToPx(dp: Int, ctx: Context) =
        (dp * ctx.resources.displayMetrics.density).toInt()


    fun show() {
        dialog.show()
    }
}