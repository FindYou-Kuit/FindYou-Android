package com.example.findu.presentation.ui.report

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentMissingReportBinding
import com.example.findu.presentation.type.report.CharacterFeatureType
import com.example.findu.presentation.type.report.ExternalFeatureType
import com.example.findu.presentation.type.report.PhysicalFeatureType
import com.example.findu.presentation.ui.report.adapter.ReportImageAdapter
import com.example.findu.presentation.ui.report.model.ReportDummys
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.adapter.ReportBreedAdapter
import com.example.findu.presentation.ui.report.adapter.ReportColorAdapter
import com.example.findu.presentation.ui.report.adapter.ReportFeatureAdapter
import com.example.findu.presentation.ui.report.dialog.ReportFinishDialog
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog
import com.example.findu.presentation.util.ViewUtils.addUnderLine
import com.example.findu.presentation.util.ViewUtils.dpToPx
import com.example.findu.presentation.util.ViewUtils.hideKeyboard
import com.example.findu.presentation.util.ViewUtils.setKeyboardVisibilityListener
import com.example.findu.presentation.util.ViewUtils.verticalScrollToYPosition
import java.time.LocalDateTime
import java.util.Calendar

class MissingReportFragment : Fragment() {
    private var _binding: FragmentMissingReportBinding? = null
    private val binding get() = _binding!!
    private val reportViewModel by viewModels<ReportViewModel>()

    private lateinit var reportImageAdapter: ReportImageAdapter
    private lateinit var breedAdapter: ArrayAdapter<String>
    private lateinit var colorAdapter: ReportColorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissingReportBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.root.setKeyboardVisibilityListener {
            binding.clMissingReportLocationContainer.visibility =
                if (it) View.GONE else View.VISIBLE
        }

        binding.btnMissingReportConfirm.setOnClickListener {
            ReportFinishDialog(
                requireContext(),
                ReportType.MISSING,
                onGoHistoryClick = ::navigateToHistory,
                onGoHomeClick = ::navigateToHome
            ).show()
        }

        with(binding.tvMissingReportLocationAddress) {
            addUnderLine()

            setOnClickListener {
                ReportLocationDialog(
                    text.toString(),
                    onSetClickListener = { newAddress ->
                        text = newAddress
                    }
                ).show(childFragmentManager, LOCATION_TAG)
            }
        }
    }

    private fun navigateToHistory() {
        // TODO : 신고 내역으로 이동하는 기능 추가
    }

    private fun navigateToHome() {
        findNavController().popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUploadImageRecyclerView()
        setUpBreedsAdapter()
        setUpColorAdapter()
        setUpFeatureAdapter()
        setUpCalender()
    }

    private fun setUpCalender() {
        val startMonth: Calendar = Calendar.getInstance().apply {
            set(2022, Calendar.JANUARY, 1)
        }
        val endMonth: Calendar = Calendar.getInstance().apply {
            set(
                LocalDateTime.now().year,
                LocalDateTime.now().monthValue - 1,
                LocalDateTime.now().dayOfMonth
            )
        }

        with(binding.cvMissingReportCalendar) {
            setVisibleMonthRange(startMonth, endMonth)
            setCurrentMonth(endMonth)
            setSelectableDateRange(startMonth, endMonth)
        }
    }

    private fun setUpFeatureAdapter() {
        binding.rvMissingReportPhysicalFeatures.adapter =
            ReportFeatureAdapter(PhysicalFeatureType.entries.toList().map { it.feature })
        binding.rvMissingReportExternalFeatures.adapter =
            ReportFeatureAdapter(ExternalFeatureType.entries.toList().map { it.feature })
        binding.rvMissingReportCharacterFeatures.adapter =
            ReportFeatureAdapter(CharacterFeatureType.entries.toList().map { it.feature })
    }

    private fun setUpColorAdapter() {
        colorAdapter = ReportColorAdapter()
        with(binding.rvMissingReportColors) {
            adapter = colorAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun setUpBreedsAdapter() {
        breedAdapter = ReportBreedAdapter(
            requireContext(),
            ReportDummys.dummyBreeds
        )

        with(binding.actvMissingReportBreed) {
            setAdapter(breedAdapter)
            setDropDownBackgroundResource(R.drawable.bg_bottom_radius_8_g4)

            setOnClickListener {
                dropDownHeight = requireContext().dpToPx(DROP_DOWN_HEIGHT)
                showDropDown()
                binding.svMissingReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
            }
            setOnItemClickListener { _, _, _, _ ->
                requireContext().hideKeyboard(windowToken)
                clearFocus()
            }
            addTextChangedListener { text ->
                ReportDummys.dummyBreeds
                    .filter { it.contains(text.toString()) }
                    .let { matches ->
                        dropDownHeight = if (matches.size > DROP_DOWN_MAX_COUNT) {
                            requireContext().dpToPx(DROP_DOWN_HEIGHT)
                        } else ViewGroup.LayoutParams.WRAP_CONTENT
                    }
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    dropDownHeight = requireContext().dpToPx(DROP_DOWN_HEIGHT)
                    showDropDown()
                    binding.svMissingReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
                }
            }
        }
    }

    private fun setupUploadImageRecyclerView() {
        reportImageAdapter = ReportImageAdapter(
            reportType = ReportType.MISSING,
            onAIButtonClick = { uri ->
                reportViewModel.getGptData(uri)
            }
        ).apply {
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
        const val LOCATION_TAG = "Report Location Dialog"
    }
}