package com.example.findu.presentation.ui.search.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.findu.R
import com.example.findu.databinding.DialogSearchFilterDateBinding
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.Type
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchFilterDateDialog(
    private val type: Type,
    private val model: SearchFilterUiModel,
    private val onUpdated: (SearchFilterUiModel) -> Unit,
) : BottomSheetDialogFragment() {

    private var _binding: DialogSearchFilterDateBinding? = null
    private val binding get() = _binding!!

    private val fmt = DateTimeFormatter.ISO_DATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.searchFilterBaseBottomSheetDialog)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = DialogSearchFilterDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

    }

    private fun initListener() = with(binding) {
        val today = LocalDate.now()
        datePicker.maxDate = System.currentTimeMillis()

        val minLocal: LocalDate? = if (type == Type.DATE_END && !model.startDate.isNullOrBlank()) {
            runCatching { LocalDate.parse(model.startDate, fmt) }.getOrNull()
        } else null

        //end
        minLocal?.let { start ->
            val cal = java.util.Calendar.getInstance().apply {
                set(start.year, start.monthValue - 1, start.dayOfMonth, 0, 0, 0)
                set(java.util.Calendar.MILLISECOND, 0)
            }
            datePicker.minDate = cal.timeInMillis
        }

        clampBounds(minLocal, today)
        applyMasks(minLocal)

        datePicker.init(
            datePicker.year,
            datePicker.month,
            datePicker.dayOfMonth
        ) { _, _, _, _ ->
            applyMasks(minLocal)
        }


        btnSearchFilterDateConfirm.setOnClickListener {
            val pickedDate = LocalDate.of(
                datePicker.year, datePicker.month + 1, datePicker.dayOfMonth
            )
            val formatted = pickedDate.format(fmt)

            if (type == Type.DATE_START) {
                model.startDate = formatted
            } else {
                model.endDate = formatted
            }
            onUpdated(model)
            dismiss()
        }

        ivSearchFilterCloseBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun clampBounds(minLocal: LocalDate?, today: LocalDate) = with(binding) {
        val cur = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
        val clamped = when {
            cur.isAfter(today) -> today
            minLocal != null && cur.isBefore(minLocal) -> minLocal
            else -> cur
        }
        if (clamped != cur) {
            datePicker.updateDate(clamped.year, clamped.monthValue - 1, clamped.dayOfMonth)
        }
    }

    private fun applyMasks(minLocal: LocalDate?) = with(binding) {
        val current = LocalDate.of(datePicker.year, datePicker.month + 1, datePicker.dayOfMonth)
        val today = LocalDate.now()

        val atToday = current.isEqual(today)
        val atStart = minLocal?.let { current.isEqual(it) } == true && type == Type.DATE_END

        flTopMask.isVisible = atStart
        flBottomMask.isVisible = atToday

        if (!atStart) flTopMask.visibility = View.GONE
        if (!atToday) flBottomMask.visibility = View.GONE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}