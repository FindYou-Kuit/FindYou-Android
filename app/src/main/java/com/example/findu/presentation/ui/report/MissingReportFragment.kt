package com.example.findu.presentation.ui.report

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentMissingReportBinding
import com.example.findu.domain.model.breed.BreedData
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.presentation.type.report.CharacterFeatureType
import com.example.findu.presentation.type.report.ExternalFeatureType
import com.example.findu.presentation.type.report.PhysicalFeatureType
import com.example.findu.presentation.ui.report.adapter.ReportImageAdapter
import com.example.findu.presentation.ui.report.model.ReportDummys
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.MissingReportFragment.Companion.DROP_DOWN_HEIGHT
import com.example.findu.presentation.ui.report.MissingReportFragment.Companion.DROP_DOWN_MAX_COUNT
import com.example.findu.presentation.ui.report.MissingReportFragment.Companion.SCROLL_OFFSET
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Calendar

@AndroidEntryPoint
class MissingReportFragment : Fragment() {
    private var _binding: FragmentMissingReportBinding? = null
    private val binding get() = _binding!!
    private val reportViewModel by viewModels<ReportViewModel>()

    private lateinit var reportImageAdapter: ReportImageAdapter
    private val breedAdapter: ReportBreedAdapter by lazy {
        ReportBreedAdapter(
            requireContext(),
            reportViewModel.selectedBreedNames.value.toMutableList()
        )
    }
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

        binding.rgMissingReportSpecies.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_missing_report_dog_button -> {
                    reportViewModel.selectSpeciesType(SpeciesType.DOG)
                }

                R.id.rb_missing_report_cat_button -> {
                    reportViewModel.selectSpeciesType(SpeciesType.CAT)
                }

                R.id.rb_missing_report_extra_button -> {
                    reportViewModel.selectSpeciesType(SpeciesType.ETC)
                }
            }
            binding.actvMissingReportBreed.text = null
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
        setUpColorAdapter()
        setUpFeatureAdapter()
        setUpCalender()


        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.currentState) {
                launch {
                    reportViewModel.breedData.collectLatest { breedData ->
                        breedData?.let { data ->
                            setUpBreedsAdapter(data)
                        }
                    }
                }

                launch {
                    reportViewModel.errorMessage.collectLatest { errorMessage ->
                        errorMessage?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                launch {
                    reportViewModel.selectedBreedNames.collectLatest { selectedBreedNames ->
                        if (selectedBreedNames.isNotEmpty())
                            breedAdapter.changeItems(selectedBreedNames)
                    }
                }
            }
        }
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

    private fun setUpBreedsAdapter(data: BreedData) {
        with(binding.actvMissingReportBreed) {
            setAdapter(breedAdapter)
            setDropDownBackgroundResource(com.example.findu.R.drawable.bg_bottom_radius_8_g4)

            setOnClickListener {
                dropDownHeight =
                    if (reportViewModel.selectedBreedNames.value.size < com.example.findu.presentation.ui.report.MissingReportFragment.DROP_DOWN_MAX_COUNT)
                        android.view.ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(com.example.findu.presentation.ui.report.MissingReportFragment.DROP_DOWN_HEIGHT)
                showDropDown()
                binding.svMissingReportContainer.verticalScrollToYPosition(com.example.findu.presentation.ui.report.MissingReportFragment.SCROLL_OFFSET)
            }
            setOnItemClickListener { _, _, _, _ ->
                requireContext().hideKeyboard(windowToken)
                clearFocus()
            }
            addTextChangedListener { text ->
                reportViewModel.selectedBreedNames.value
                    .filter { it.contains(text.toString()) }
                    .let { matches ->
                        dropDownHeight = if (matches.size > DROP_DOWN_MAX_COUNT) {
                            requireContext().dpToPx(DROP_DOWN_HEIGHT)
                        } else ViewGroup.LayoutParams.WRAP_CONTENT
                    }
            }
            setOnFocusChangeListener { _, hasFocus ->
                dropDownHeight =
                    if (reportViewModel.selectedBreedNames.value.size < com.example.findu.presentation.ui.report.MissingReportFragment.DROP_DOWN_MAX_COUNT)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(com.example.findu.presentation.ui.report.MissingReportFragment.DROP_DOWN_HEIGHT)
                if (hasFocus) {
                    showDropDown()
                    binding.svMissingReportContainer.verticalScrollToYPosition(com.example.findu.presentation.ui.report.MissingReportFragment.SCROLL_OFFSET)
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
        const val LOCATION_TAG = "Report Location Dialog"
    }
}