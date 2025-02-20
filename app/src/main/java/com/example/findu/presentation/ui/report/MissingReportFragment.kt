package com.example.findu.presentation.ui.report

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.archit.calendardaterangepicker.customviews.CalendarListener
import com.example.findu.R
import com.example.findu.databinding.FragmentMissingReportBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.SexType
import com.example.findu.presentation.ui.report.model.ReportUiState
import com.example.findu.presentation.type.report.CharacterFeatureType
import com.example.findu.presentation.type.report.ExternalFeatureType
import com.example.findu.presentation.type.report.PhysicalFeatureType
import com.example.findu.presentation.type.report.ReportFeature
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.adapter.ReportBreedAdapter
import com.example.findu.presentation.ui.report.adapter.ReportColorAdapter
import com.example.findu.presentation.ui.report.adapter.ReportFeatureAdapter
import com.example.findu.presentation.ui.report.constants.ReportConstants.DROP_DOWN_HEIGHT
import com.example.findu.presentation.ui.report.constants.ReportConstants.DROP_DOWN_MAX_COUNT
import com.example.findu.presentation.ui.report.constants.ReportConstants.IMAGE_RESULT_KEY
import com.example.findu.presentation.ui.report.constants.ReportConstants.IMAGE_URI
import com.example.findu.presentation.ui.report.constants.ReportConstants.LOCATION_TAG
import com.example.findu.presentation.ui.report.constants.ReportConstants.SCROLL_OFFSET
import com.example.findu.presentation.ui.report.adapter.ReportImageAdapter
import com.example.findu.presentation.ui.report.dialog.ReportFinishDialog
import com.example.findu.presentation.ui.report.dialog.ReportImageDialog
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog
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
class MissingReportFragment : Fragment() {
    private var _binding: FragmentMissingReportBinding? = null
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

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissingReportBinding.inflate(inflater, container, false)

        initListener()
        getCapturedUri()
        getUploadedUri()

        return binding.root
    }

    private fun getUploadedUri() {
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    reportViewModel.addImageUri(uri)
                } else {
                    Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getCapturedUri() {
        setFragmentResultListener(IMAGE_URI) { _, result ->
            val imageUri = result.getString(IMAGE_RESULT_KEY)
            imageUri?.let { reportViewModel.addImageUri(Uri.parse(imageUri)) }
        }

    }

    private fun initListener() {
        binding.clMissingReportBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.root.setKeyboardVisibilityListener {
            binding.clMissingReportLocationContainer.visibility =
                if (it) View.GONE else View.VISIBLE
        }

        binding.btnMissingReportConfirm.setOnClickListener {
            reportViewModel.postMissingReport(
                description = binding.etMissingReportDescription.text.toString(),
            )
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

            addTextChangedListener { text ->
                reportViewModel.updateReportData(
                    location = text.toString()
                )
            }
        }

        binding.rgMissingReportSpecies.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_missing_report_dog_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.DOG, breedName = ""
                    )
                }

                R.id.rb_missing_report_cat_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.CAT, breedName = ""
                    )
                }

                R.id.rb_missing_report_extra_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.ETC, breedName = ""
                    )
                }
            }
            reportViewModel.updateReportData(
                breedName = null
            )
            binding.actvMissingReportBreed.text = null
        }

        binding.rgMissingReportGenders.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_missing_report_male_button -> {
                    reportViewModel.updateReportData(
                        sexType = SexType.MALE
                    )
                }

                R.id.rb_missing_report_female_button -> {
                    reportViewModel.updateReportData(
                        sexType = SexType.FEMALE
                    )
                }

                R.id.rb_missing_report_unknown_button -> {
                    reportViewModel.updateReportData(
                        sexType = SexType.UNKNOWN
                    )
                }
            }
        }

    }

    private fun navigateToHistory() {
        findNavController().navigate(R.id.action_fragment_missing_report_to_fragment_my_report_history)
    }

    private fun navigateToHome() {
        findNavController().navigate(
            R.id.action_fragment_witness_report_to_fragment_home,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.fragment_witness_report, true) // fragment_witness_report 포함 이전 스택 제거
                .setLaunchSingleTop(true) // 중복 생성 방지
                .build()
        )
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
                    reportViewModel.imageUriList.collectLatest { imageUriList ->
                        with(reportImageAdapter) {
                            submitList(imageUriList) {
                                notifyItemChanged(0)
                            }
                        }
                    }
                }
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
                    reportViewModel.reportUiState.collectLatest { uiState ->
                        when (uiState) {
                            ReportUiState.Default -> {
                                binding.lavMissingReportLoading.visibility = View.GONE
                                binding.btnMissingReportConfirm.isEnabled = false
                            }

                            ReportUiState.Loading -> {
                                binding.lavMissingReportLoading.visibility = View.VISIBLE
                            }

                            ReportUiState.Enable -> {
                                binding.btnMissingReportConfirm.isEnabled = true
                            }

                            ReportUiState.Finished -> {
                                binding.lavMissingReportLoading.visibility = View.GONE
                                ReportFinishDialog(
                                    requireContext(),
                                    ReportType.MISSING,
                                    onGoHistoryClick = ::navigateToHistory,
                                    onGoHomeClick = ::navigateToHome
                                ).show()
                            }

                            else -> {
                                binding.lavMissingReportLoading.visibility = View.GONE
                            }
                        }
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
            setSelectedDateRange(Calendar.getInstance(), Calendar.getInstance())
            reportViewModel.updateReportData(
                date = Calendar.getInstance().time
            )
            setCalendarListener(object : CalendarListener {
                override fun onDateRangeSelected(startDate: Calendar, endDate: Calendar) {
                    reportViewModel.updateReportData(
                        date = startDate.time
                    )
                }

                override fun onFirstDateSelected(startDate: Calendar) {}

            })
        }
    }

    private fun setUpFeatureAdapter() {
        binding.rvMissingReportPhysicalFeatures.adapter =
            ReportFeatureAdapter(
                features = PhysicalFeatureType.entries.toList().map {
                    ReportFeature(it.feature, it.featureId)
                },
                onFeatureClick = {
                    reportViewModel.updateReportData(
                        featureIds = it
                    )
                })

        binding.rvMissingReportExternalFeatures.adapter =
            ReportFeatureAdapter(
                features = ExternalFeatureType.entries.toList().map {
                    ReportFeature(it.feature, it.featureId)
                },
                onFeatureClick = {
                    reportViewModel.updateReportData(
                        featureIds = it
                    )
                })

        binding.rvMissingReportCharacterFeatures.adapter =
            ReportFeatureAdapter(
                features = CharacterFeatureType.entries.toList().map {
                    ReportFeature(it.feature, it.featureId)
                },
                onFeatureClick = {
                    reportViewModel.updateReportData(
                        featureIds = it
                    )
                })
    }

    private fun setUpColorAdapter() {
        colorAdapter = ReportColorAdapter { furColor ->
            reportViewModel.updateReportData(
                furColor = furColor
            )
        }

        with(binding.rvMissingReportColors) {
            adapter = colorAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun setUpBreedsAdapter() {
        with(binding.actvMissingReportBreed) {
            setAdapter(breedAdapter)
            setDropDownBackgroundResource(R.drawable.bg_bottom_radius_8_g4)

            setOnClickListener {
                dropDownHeight =
                    if (reportViewModel.selectedBreedList.value.size < DROP_DOWN_MAX_COUNT)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(DROP_DOWN_HEIGHT)
                showDropDown()
                binding.svMissingReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
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
                    binding.svMissingReportContainer.verticalScrollToYPosition(SCROLL_OFFSET)
                }
            }
        }
    }

    private fun setupUploadImageRecyclerView() {
        val dialog = ReportImageDialog(
            requireContext(),
            onCapture = {
                findNavController().navigate(R.id.action_fragment_missing_report_to_fragment_report_camera)
            },
            onUpload = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )
        reportImageAdapter = ReportImageAdapter(
            context = requireContext(),
            reportType = ReportType.MISSING,
            onRemoveClickListener = { position -> reportViewModel.removeImageUriPosition(position) },
            onUploadClickListener = { dialog.show() },
        ).apply {
            submitList(reportViewModel.imageUriList.value)
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
}