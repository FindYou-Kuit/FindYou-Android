package com.example.findu.presentation.util

import android.content.Context
import android.graphics.Rect
import android.os.IBinder
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import android.widget.TextView

object ViewUtils {
    fun Context.dpToPx(dp: Float): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale).toInt()
    }

    fun Context.dpToPx(dp: Int) = dpToPx(dp.toFloat())

    fun Context.hideKeyboard(windowToken: IBinder?) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.setKeyboardVisibilityListener(onKeyboardVisibilityChanged: (Boolean) -> Unit) {
        val rootView = this
        var isKeyboardVisible = false

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)

            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            // 키보드가 화면의 15% 이상 차지하면 키보드가 나타났다고 간주
            val isVisible = keypadHeight > screenHeight * 0.15

            if (isKeyboardVisible != isVisible) {
                isKeyboardVisible = isVisible
                onKeyboardVisibilityChanged(isVisible)
            }
        }
    }

    fun ScrollView.verticalScrollToYPosition(y: Int) {
        this.post {
            this.smoothScrollTo(0, y)
        }
    }

    fun TextView.addUnderLine() {
        val content = SpannableString(this.text.toString())
        content.setSpan(UnderlineSpan(), 0, text.length, 0)
        this.text = content

    }
}