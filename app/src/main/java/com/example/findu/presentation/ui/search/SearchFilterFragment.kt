package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchFilterBinding
import com.example.findu.presentation.ui.search.dialog.SearchFilterDateDialog
import com.example.findu.presentation.ui.search.model.SearchFilterUiModel
import com.example.findu.presentation.ui.search.model.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SearchFilterFragment : Fragment() {

    private var _binding: FragmentSearchFilterBinding? = null
    private val binding get() = _binding!!

    private val fmt = DateTimeFormatter.ISO_DATE
    private val filterModel = SearchFilterUiModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

    }

    private fun initListener() = with(binding) {
        clSearchFilterDateStart.setOnClickListener {
            SearchFilterDateDialog(Type.DATE_START, filterModel) { updated ->
                tvSearchFilterDateStart.text = updated.startDate
                tvSearchFilterDateStart.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.gray6)
                )
                updated.endDate?.let { end ->
                    if (LocalDate.parse(end, fmt)
                            .isBefore(LocalDate.parse(updated.startDate, fmt))
                    ) {
                        filterModel.endDate = null
                        tvSearchFilterDateEnd.text =
                            getString(R.string.search_filter_date_input_end)
                        tvSearchFilterDateEnd.setTextColor(
                            ContextCompat.getColor(requireContext(), R.color.gray4)
                        )
                    }
                }
            }.show(parentFragmentManager, "date_start")
        }

        clSearchFilterDateEnd.setOnClickListener {
            SearchFilterDateDialog(Type.DATE_END, filterModel) { updated ->
                tvSearchFilterDateEnd.text = updated.endDate
                tvSearchFilterDateEnd.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.gray6)
                )
            }.show(parentFragmentManager, "date_end")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}