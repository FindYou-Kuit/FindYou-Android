package com.example.findu.presentation.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.example.findu.R
import com.example.findu.databinding.FragmentWitnessReportBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.GptData
import com.example.findu.domain.model.report.SexType
import com.example.findu.presentation.ui.report.model.GptUiState
import com.example.findu.presentation.ui.report.model.ReportUiState
import com.example.findu.presentation.type.report.CharacterFeatureType
import com.example.findu.presentation.type.report.ExternalFeatureType
import com.example.findu.presentation.type.report.PhysicalFeatureType
import com.example.findu.presentation.type.report.ReportFeature
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.adapter.ReportBreedAdapter
import com.example.findu.presentation.ui.report.adapter.ReportColorAdapter
import com.example.findu.presentation.ui.report.adapter.ReportFeatureAdapter
import com.example.findu.presentation.ui.report.adapter.ReportImageAdapter
import com.example.findu.presentation.ui.report.constants.ReportConstants.DROP_DOWN_HEIGHT
import com.example.findu.presentation.ui.report.constants.ReportConstants.DROP_DOWN_MAX_COUNT
import com.example.findu.presentation.ui.report.constants.ReportConstants.LOCATION_TAG
import com.example.findu.presentation.ui.report.constants.ReportConstants.SCROLL_OFFSET
import com.example.findu.presentation.ui.report.dialog.ReportFinishDialog
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog
import com.example.findu.presentation.ui.report.model.ReportDummys
import com.example.findu.presentation.ui.report.viewmodel.ReportViewModel
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
class WitnessReportFragment : Fragment() {
    private var _binding: FragmentWitnessReportBinding? = null
    private val binding get() = _binding!!
    private val reportViewModel by viewModels<ReportViewModel>()

    private lateinit var reportImageAdapter: ReportImageAdapter
    private val breedAdapter: ReportBreedAdapter by lazy {
        ReportBreedAdapter(
            requireContext(),
            reportViewModel.selectedBreedList.value.toMutableList()
        )
    }
    private lateinit var colorAdapter: ReportColorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWitnessReportBinding.inflate(inflater, container, false)

        initListener()
        reportViewModel.updateReportData(
            sexType = SexType.UNKNOWN
        )

        return binding.root
    }

    private fun initListener() {
        binding.root.setKeyboardVisibilityListener {
            binding.clWitnessReportLocationContainer.visibility =
                if (it) View.GONE else View.VISIBLE
        }

        binding.btnWitnessReportConfirm.setOnClickListener {
            reportViewModel.postWitnessReport(
                description = binding.etWitnessReportDescription.text.toString()
            )
        }

        with(binding.tvWitnessReportLocationAddress) {
            addUnderLine()

            setOnClickListener {
                ReportLocationDialog(
                    text.toString(),
                    onSetClickListener = { newAddress ->
                        text = newAddress
                    }
                ).show(childFragmentManager, LOCATION_TAG)
            }

            addTextChangedListener { text ->
                reportViewModel.updateReportData(
                    location = text.toString()
                )
            }
        }

        binding.rgWitnessReportSpecies.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_witness_report_dog_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.DOG
                    )
                }

                R.id.rb_witness_report_cat_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.CAT
                    )
                }

                R.id.rb_witness_report_extra_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.ETC
                    )
                }
            }
            binding.actvWitnessReportBreed.text = null
        }
    }

    private fun navigateToHistory() {
        // TODO : 신고 내역으로 이동하는 기능 추가
    }

    private fun navigateToHome() {
        findNavController()
            .navigate(R.id.action_fragment_witness_report_to_fragment_home)
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
                        breedData?.let {
                            setUpBreedsAdapter()
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
                    reportViewModel.selectedBreedList.collectLatest { selectedBreedNames ->
                        if (selectedBreedNames.isNotEmpty())
                            breedAdapter.changeItems(selectedBreedNames)
                    }
                }

                launch {
                    reportViewModel.gptData.collectLatest { gptData ->
                        gptData?.let {
                            setSpecies(gptData)
                            setBreedName(gptData)
                            setFurColors(gptData)
                        }
                    }
                }

                launch {
                    reportViewModel.gptUiState.collectLatest { uiState ->
                        when (uiState) {
                            GptUiState.Loading -> {
                                binding.pbWitnessReportLoading.visibility = View.VISIBLE
                            }

                            GptUiState.Default, GptUiState.Finished -> {
                                binding.pbWitnessReportLoading.visibility = View.GONE
                            }
                        }
                    }
                }

                launch {
                    reportViewModel.reportUiState.collectLatest { uiState ->
                        when (uiState) {
                            ReportUiState.Default -> {
                                binding.btnWitnessReportConfirm.isEnabled = false
                            }

                            ReportUiState.Loading -> {
                                binding.pbWitnessReportLoading.visibility = View.VISIBLE
                            }

                            ReportUiState.Enable -> {
                                binding.btnWitnessReportConfirm.isEnabled = true
                            }

                            ReportUiState.Finished -> {
                                binding.pbWitnessReportLoading.visibility = View.GONE
                                ReportFinishDialog(
                                    requireContext(),
                                    ReportType.MISSING,
                                    onGoHistoryClick = ::navigateToHistory,
                                    onGoHomeClick = ::navigateToHome
                                ).show()
                            }

                            else -> {
                                binding.pbWitnessReportLoading.visibility = View.GONE
                            }
                        }
                    }

                }
            }
        }
    }

    private fun setFurColors(gptData: GptData) {
        colorAdapter.updateSelectedColors(gptData.furColors)
    }

    private fun setBreedName(gptData: GptData) {
        if (gptData.breed.isEmpty())
            binding.actvWitnessReportBreed.setHint(R.string.report_cannot_distinction)
        else
            binding.actvWitnessReportBreed.setHint(R.string.report_breed_input_hint)
        binding.actvWitnessReportBreed.setText(gptData.breed)
    }

    private fun setSpecies(gptData: GptData) {
        when (gptData.species) {
            SpeciesType.DOG -> {
                binding.rbWitnessReportDogButton.isChecked = true
                reportViewModel.updateReportData(speciesType = SpeciesType.DOG)
            }

            SpeciesType.CAT -> {
                binding.rbWitnessReportCatButton.isChecked = true
                reportViewModel.updateReportData(speciesType = SpeciesType.CAT)
            }

            SpeciesType.ETC -> {
                binding.rbWitnessReportExtraButton.isChecked = true
                reportViewModel.updateReportData(speciesType = SpeciesType.ETC)
            }

            null -> {}
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

        with(binding.cvWitnessReportCalendar) {
            setVisibleMonthRange(startMonth, endMonth)
            setCurrentMonth(endMonth)
            setSelectableDateRange(startMonth, endMonth)

            setCalendarListener(object : CalendarListener {
                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    reportViewModel.updateReportData(
                        missingDate = startDate.time
                    )
                }

                override fun onFirstDateSelected(startDate: Calendar) {}
            })
        }
    }

    private fun setUpFeatureAdapter() {
        binding.rvWitnessReportPhysicalFeatures.adapter =
            ReportFeatureAdapter(
                features = PhysicalFeatureType.entries.toList().map {
                    ReportFeature(it.feature, it.featureId)
                }) { featureId ->
                reportViewModel.updateReportData(
                    featureIds = featureId
                )
            }

        binding.rvWitnessReportExternalFeatures.adapter =
            ReportFeatureAdapter(
                features = ExternalFeatureType.entries.toList().map {
                    ReportFeature(it.feature, it.featureId)
                }) { featureId ->
                reportViewModel.updateReportData(
                    featureIds = featureId
                )
            }

        binding.rvWitnessReportCharacterFeatures.adapter =
            ReportFeatureAdapter(
                features = CharacterFeatureType.entries.toList().map {
                    ReportFeature(it.feature, it.featureId)
                }) { featureId ->
                reportViewModel.updateReportData(
                    featureIds = featureId
                )
            }
    }

    private fun setUpColorAdapter() {
        colorAdapter = ReportColorAdapter { furColor ->
            reportViewModel.updateReportData(
                furColor = furColor
            )
        }
        with(binding.rvWitnessReportColors) {
            adapter = colorAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun setUpBreedsAdapter() {
        with(binding.actvWitnessReportBreed) {
            setAdapter(breedAdapter)
            setDropDownBackgroundResource(R.drawable.bg_bottom_radius_8_g4)

            setOnClickListener {
                dropDownHeight =
                    if (reportViewModel.selectedBreedList.value.size < DROP_DOWN_MAX_COUNT)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(DROP_DOWN_HEIGHT)
                showDropDown()
                binding.svWitnessReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
            }
            setOnItemClickListener { _, _, _, _ ->
                reportViewModel.updateReportData(
                    breedName = text.toString()
                )
                requireContext().hideKeyboard(windowToken)
                clearFocus()
            }
            addTextChangedListener { text ->
                reportViewModel.selectedBreedList.value
                    .filter { it.contains(text.toString()) }
                    .let { matches ->
                        dropDownHeight = if (matches.size > DROP_DOWN_MAX_COUNT) {
                            requireContext().dpToPx(DROP_DOWN_HEIGHT)
                        } else ViewGroup.LayoutParams.WRAP_CONTENT
                    }
            }
            setOnFocusChangeListener { _, hasFocus ->
                dropDownHeight =
                    if (reportViewModel.selectedBreedList.value.size < DROP_DOWN_MAX_COUNT)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(DROP_DOWN_HEIGHT)
                if (hasFocus) {
                    showDropDown()
                    binding.svWitnessReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
                }
            }
        }
    }

    private fun setupUploadImageRecyclerView() {
        reportImageAdapter = ReportImageAdapter(
            reportType = ReportType.WITNESS,
            onAIButtonClick = { uri ->
                reportViewModel.getGptData(uri)
            }).apply {
            submitList(ReportDummys.dummyImageUris)
        }
        with(binding.rvWitnessReportImages) {
            adapter = reportImageAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.root.viewTreeObserver.removeOnGlobalLayoutListener { }

        _binding = null
    }
}