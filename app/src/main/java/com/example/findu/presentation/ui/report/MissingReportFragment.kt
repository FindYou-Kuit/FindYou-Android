package com.example.findu.presentation.ui.report

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.databinding.FragmentMissingRepostBinding
import com.example.findu.databinding.ItemReportBreedsBinding
import com.example.findu.presentation.ui.report.adapter.ReportImageAdapter
import com.example.findu.presentation.ui.report.model.ReportDummys
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.adapter.ReportBreedAdapter
import com.example.findu.presentation.util.ViewUtils.dpToPx
import com.example.findu.presentation.util.ViewUtils.hideKeyboard
import com.example.findu.presentation.util.ViewUtils.setKeyboardVisibilityListener
import com.example.findu.presentation.util.ViewUtils.verticalScrollToYPosition
import dagger.hilt.android.qualifiers.ApplicationContext

class MissingReportFragment : Fragment() {
    private var _binding: FragmentMissingRepostBinding? = null
    private val binding get() = _binding!!
    private val reportViewModel by viewModels<ReportViewModel>()

    private lateinit var reportImageAdapter: ReportImageAdapter
    private lateinit var breedAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissingRepostBinding.inflate(inflater, container, false)

        binding.root.setKeyboardVisibilityListener {
            binding.clMissingReportLocationContainer.visibility =
                if (it) View.GONE else View.VISIBLE
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUploadImageRecyclerView()
        setUpBreedsAdapter()

    }


    private fun setUpBreedsAdapter() {
        breedAdapter = ReportBreedAdapter(
            requireContext(),
            ReportDummys.dummyBreeds
        )

        with(binding.actvMissingReportBreed) {
            setAdapter(breedAdapter)
            setDropDownBackgroundResource(R.drawable.bg_bottom_radius_8_g4)

            // 클릭하면 드랍다운이 생김
            setOnClickListener {
                dropDownHeight = requireContext().dpToPx(DROP_DOWN_HEIGHT)
                showDropDown()
                binding.svMissingReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
            }
            // 드랍다운 아이템이 선택되면 소프트 키보드가 사라지고, 하단 레이아웃이 보임
            setOnItemClickListener { _, _, _, _ ->
                requireContext().hideKeyboard(windowToken)
                clearFocus()
            }
            // text 가 변경되면 드랍다운의 크기를 줄임
            addTextChangedListener { text ->
                ReportDummys.dummyBreeds
                    .filter { it.contains(text.toString()) }
                    .let { matches ->
                        dropDownHeight = if (matches.size > DROP_DOWN_MAX_COUNT) {
                            requireContext().dpToPx(DROP_DOWN_HEIGHT)
                        } else ViewGroup.LayoutParams.WRAP_CONTENT
                    }
            }
            // focus 가 생기면 품종을 화면 상단으로 이동시킴
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    showDropDown()
                    binding.svMissingReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
                }
            }
        }
    }

    private fun setupUploadImageRecyclerView() {
        reportImageAdapter = ReportImageAdapter(ReportType.MISSING).apply {
            submitList(ReportDummys.dummyImageUris)
        }
        with(binding.rvMissingReportImages) {
            adapter = reportImageAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.root.viewTreeObserver.removeOnGlobalLayoutListener { }

        _binding = null
    }

    companion object {
        const val SCROLL_OFFSET = 258
        const val DROP_DOWN_HEIGHT = 248
        const val DROP_DOWN_MAX_COUNT = 8
    }
}