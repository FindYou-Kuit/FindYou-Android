package com.example.findu.presentation.ui.my

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.findu.databinding.FragmentMyInquireBinding
import com.example.findu.R

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
        val mainColor = ContextCompat.getColor(requireContext(), R.color.main_color)
        val grayColor = ContextCompat.getColor(requireContext(), R.color.gray3)

        binding.tfInquireTitle.setOnFocusChangeListener { _, hasFocus ->
            val text = binding.tfInquireTitle.text?.toString()
            binding.tfiInquireTitle.boxStrokeColor =
                if (hasFocus || !text.isNullOrBlank()) mainColor else grayColor
        }

        binding.tfInquireContent.setOnFocusChangeListener { _, hasFocus ->
            val text = binding.tfInquireContent.text?.toString()
            binding.tfiInquireContent.boxStrokeColor =
                if (hasFocus || !text.isNullOrBlank()) mainColor else grayColor
        }
    }

    private fun initChipStrokeColors() {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.main_color)
        val defaultColor = ContextCompat.getColor(requireContext(), R.color.gray3)

        val chipList = listOf(
            binding.chipBug,
            binding.chipFeedback,
            binding.chipEtc
        )

        chipList.forEach { chip ->
            chip.chipStrokeWidth = 1f

            chip.chipStrokeColor = ColorStateList.valueOf(
                if (chip.isChecked) selectedColor else defaultColor
            )

            chip.setOnCheckedChangeListener { _, isChecked ->
                chip.chipStrokeColor = ColorStateList.valueOf(
                    if (isChecked) selectedColor else defaultColor
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