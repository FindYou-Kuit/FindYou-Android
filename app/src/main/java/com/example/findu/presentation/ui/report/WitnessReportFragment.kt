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
import com.example.findu.databinding.FragmentWitnessReportBinding
import com.example.findu.domain.model.breed.SpeciesType
import com.example.findu.domain.model.report.GptData
import com.example.findu.presentation.type.report.CharacterFeatureType
import com.example.findu.presentation.type.report.ExternalFeatureType
import com.example.findu.presentation.type.report.PhysicalFeatureType
import com.example.findu.presentation.type.report.ReportFeature
import com.example.findu.presentation.type.report.ReportType
import com.example.findu.presentation.ui.report.adapter.ReportBreedAdapter
import com.example.findu.presentation.ui.report.adapter.ReportColorAdapter
import com.example.findu.presentation.ui.report.adapter.ReportFeatureAdapter
import com.example.findu.presentation.ui.report.adapter.ReportImageAdapter
import com.example.findu.presentation.ui.report.constants.ReportConstants
import com.example.findu.presentation.ui.report.constants.ReportConstants.IMAGE_RESULT_KEY
import com.example.findu.presentation.ui.report.constants.ReportConstants.IMAGE_URI
import com.example.findu.presentation.ui.report.dialog.ReportFinishDialog
import com.example.findu.presentation.ui.report.dialog.ReportImageDialog
import com.example.findu.presentation.ui.report.dialog.ReportLocationDialog
import com.example.findu.presentation.ui.report.model.GptUiState
import com.example.findu.presentation.ui.report.model.ReportUiState
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

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWitnessReportBinding.inflate(inflater, container, false)

        initListener()
        reportViewModel.updateReportData(
//            gender = Gender.UNKNOWN
        )
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
        binding.clWitnessReportBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

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
                ).show(childFragmentManager, ReportConstants.LOCATION_TAG)
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
                        speciesType = SpeciesType.DOG, breedName = ""
                    )
                }

                R.id.rb_witness_report_cat_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.CAT, breedName = ""
                    )
                }

                R.id.rb_witness_report_extra_button -> {
                    reportViewModel.updateReportData(
                        speciesType = SpeciesType.ETC, breedName = ""
                    )
                }
            }
            binding.actvWitnessReportBreed.text = null
        }
    }

    private fun navigateToHistory() {
        findNavController().navigate(R.id.action_fragment_witness_report_to_fragment_my_report_history)

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
                    reportViewModel.errorMessage.collectLatest { errorMessage ->
                        errorMessage?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                                .show()
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
                                binding.lavWitnessReportLoading.visibility = View.VISIBLE
                            }

                            GptUiState.Default, GptUiState.Finished -> {
                                binding.lavWitnessReportLoading.visibility = View.GONE
                            }
                        }
                    }
                }

                launch {
                    reportViewModel.reportUiState.collectLatest { uiState ->
                        when (uiState) {
                            ReportUiState.Default -> {
                                binding.lavWitnessReportLoading.visibility = View.GONE
                                binding.btnWitnessReportConfirm.isEnabled = false
                            }

                            ReportUiState.Loading -> {
                                binding.lavWitnessReportLoading.visibility = View.VISIBLE
                            }

                            ReportUiState.Enable -> {
                                binding.btnWitnessReportConfirm.isEnabled = true
                            }

                            ReportUiState.Finished -> {
                                binding.lavWitnessReportLoading.visibility = View.GONE
                                ReportFinishDialog(
                                    requireContext(),
                                    ReportType.MISSING,
                                    onGoHistoryClick = ::navigateToHistory,
                                    onGoHomeClick = ::navigateToHome
                                ).show()
                            }

                            else -> {
                                binding.lavWitnessReportLoading.visibility = View.GONE
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
            setSelectedDateRange(Calendar.getInstance(), Calendar.getInstance())
            reportViewModel.updateReportData(
                date = Calendar.getInstance().time
            )
            binding.cvWitnessReportCalendar.setCalendarListener(object : CalendarListener {
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
                    if (reportViewModel.selectedBreedList.value.size < ReportConstants.DROP_DOWN_MAX_COUNT)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(ReportConstants.DROP_DOWN_HEIGHT)
                showDropDown()
                binding.svWitnessReportContainer.verticalScrollToYPosition(ReportConstants.SCROLL_OFFSET)
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
                        dropDownHeight =
                            if (matches.size > ReportConstants.DROP_DOWN_MAX_COUNT) {
                                requireContext().dpToPx(ReportConstants.DROP_DOWN_HEIGHT)
                            } else ViewGroup.LayoutParams.WRAP_CONTENT
                    }
            }
            setOnFocusChangeListener { _, hasFocus ->
                dropDownHeight =
                    if (reportViewModel.selectedBreedList.value.size < ReportConstants.DROP_DOWN_MAX_COUNT)
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    else requireContext().dpToPx(ReportConstants.DROP_DOWN_HEIGHT)
                if (hasFocus) {
                    showDropDown()
                    binding.svWitnessReportContainer.verticalScrollToYPosition(
                        ReportConstants.SCROLL_OFFSET
                    )
                }
            }
        }
    }

    private fun setupUploadImageRecyclerView() {
        val dialog = ReportImageDialog(
            requireContext(),
            onCapture = {
                findNavController().navigate(R.id.action_fragment_witness_report_to_fragment_report_camera)
            },
            onUpload = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        )

        reportImageAdapter = ReportImageAdapter(
            context = requireContext(),
            reportType = ReportType.WITNESS,
            onRemoveClickListener = { position ->
                reportViewModel.removeImageUriPosition(
                    position
                )
            },
            onUploadClickListener = { dialog.show() },
            onAIButtonClick = { uri ->
                reportViewModel.getGptData(uri)
            }
        ).apply {
            submitList(reportViewModel.imageUriList.value)
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