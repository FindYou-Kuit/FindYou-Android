package com.kuit.findu.presentation.ui.my

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.kuit.findu.databinding.FragmentMyInquireBinding
import com.kuit.findu.R
import com.google.android.material.textfield.TextInputLayout

class MyInquireFragment : Fragment() {

    private var _binding: FragmentMyInquireBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyInquireBinding.inflate(inflater, container, false)
        initListener()
        return binding.root
    }

    private fun initListener() {
        initChipStrokeColors()
        initTextFieldColors()

        binding.btnInquireSetInitial.setOnClickListener {
            binding.tfInquireTitle.text?.clear()
            binding.tfInquireContent.text?.clear()

            val chipGroup = binding.cgInquireFilter
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i)
                if (chip is com.google.android.material.chip.Chip) {
                    chip.isChecked = false
                }
            }
        }

        binding.btnInquireCheck.setOnClickListener {
            val title = binding.tfInquireTitle.text?.toString()?.trim()
            val content = binding.tfInquireContent.text?.toString()?.trim()
            val hasSelectedChip = listOf(
                binding.chipBug,
                binding.chipFeedback,
                binding.chipEtc
            ).any { it.isChecked }

            if (title.isNullOrEmpty() || content.isNullOrEmpty() || !hasSelectedChip) {
                showCustomToast("모든 내용을 채워주세요!")
                return@setOnClickListener
            }

            parentFragmentManager.popBackStack()
        }

        binding.ivMyInquireBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initTextFieldColors() {
        binding.tfInquireTitle.addTextChangedListener { applyTitleStroke() }
        binding.tfInquireTitle.setOnFocusChangeListener { _, _ -> applyTitleStroke() }
        binding.tfInquireContent.addTextChangedListener { applyContentStroke() }
        binding.tfInquireContent.setOnFocusChangeListener { _, _ -> applyContentStroke() }

        applyTitleStroke()
        applyContentStroke()
    }

    private fun applyTitleStroke() {
        binding.tfiInquireTitle.setBoxStrokeWidthFocused(1f.toInt())
        val main = ContextCompat.getColor(requireContext(), R.color.main_color)
        val gray = ContextCompat.getColor(requireContext(), R.color.gray3)
        val keepMain = !binding.tfInquireTitle.text.isNullOrBlank() || binding.tfInquireTitle.hasFocus()
        setStroke(binding.tfiInquireTitle, if (keepMain) main else gray)
    }

    private fun applyContentStroke() {
        binding.tfiInquireContent.setBoxStrokeWidthFocused(1f.toInt())
        val main = ContextCompat.getColor(requireContext(), R.color.main_color)
        val gray = ContextCompat.getColor(requireContext(), R.color.gray3)
        val keepMain = !binding.tfInquireContent.text.isNullOrBlank() || binding.tfInquireContent.hasFocus()
        setStroke(binding.tfiInquireContent, if (keepMain) main else gray)
    }

    private fun setStroke(til: TextInputLayout, color: Int) {
        val stateList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_focused),
                intArrayOf(android.R.attr.state_hovered),
                intArrayOf()
            ),
            intArrayOf(color, color, color, color)
        )
        til.setBoxStrokeColorStateList(stateList)
    }

    private fun initChipStrokeColors() {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.main_color)
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray3)
        val defaultTextColor = ContextCompat.getColor(requireContext(), R.color.gray5)
        fun Float.dpToPx(): Float {
            return this * resources.displayMetrics.density
        }
        val selectedWidth = 1.5f.dpToPx()
        val defaultWidth = 1f.dpToPx()

        val chipList = listOf(
            binding.chipBug,
            binding.chipFeedback,
            binding.chipEtc
        )

        chipList.forEach { chip ->
            chip.chipStrokeColor = ColorStateList.valueOf(
                if (chip.isChecked) selectedColor else defaultColor
            )
            chip.setOnCheckedChangeListener { _, isChecked ->
                chip.chipStrokeColor = ColorStateList.valueOf(
                    if (isChecked) selectedColor else defaultColor
                )
                chip.chipStrokeWidth = if (isChecked) selectedWidth else defaultWidth
                chip.setTextAppearance(
                    if(isChecked) R.style.TextAppearance_FindU_Body2_SB_14 else R.style.TextAppearance_FindU_Body2_R_14
                )
                chip.setTextColor(
                    if (isChecked) selectedColor else defaultTextColor
                )
            }

            chip.apply { minHeight = 54 }
        }
    }

    private fun showCustomToast(message: String) {
        val toastView = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_toast_inquire, null)

        val tvToastText = toastView.findViewById<TextView>(R.id.tv_toast_text)
        tvToastText.text = message

        Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            view = toastView
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 150)
        }.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}