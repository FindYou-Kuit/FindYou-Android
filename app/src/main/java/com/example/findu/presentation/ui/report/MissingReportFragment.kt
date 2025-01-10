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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUploadImageRecyclerView()
        setUpBreedsAdapter(requireContext())

    }


    private fun setUpBreedsAdapter(context: Context) {
        breedAdapter = ReportBreedAdapter(
            requireContext(),
            ReportDummys.dummyBreeds
        )

        with(binding.actvMissingReportBreed) {
            setAdapter(breedAdapter)
            setDropDownBackgroundResource(R.drawable.bg_bottom_radius_8_g4)


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
        _binding = null
    }

    companion object {
        private const val SCROLL_OFFSET = 258
        private const val DROP_DOWN_HEIGHT = 248
        private const val DROP_DOWN_MAX_COUNT = 8
    }
}