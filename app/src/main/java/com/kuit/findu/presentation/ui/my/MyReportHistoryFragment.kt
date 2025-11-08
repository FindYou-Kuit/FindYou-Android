package com.kuit.findu.presentation.ui.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.findu.databinding.FragmentMyReportHistoryBinding
import com.kuit.findu.presentation.ui.my.adapter.MyReportHistoryAdapter
import com.kuit.findu.presentation.ui.my.dialog.MyDeleteHistoryDialog
import com.kuit.findu.presentation.ui.my.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyReportHistoryFragment : Fragment() {
    private var _binding: FragmentMyReportHistoryBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    private val myReportHistoryAdapter by lazy {
        MyReportHistoryAdapter(
            onDeleteClick = { reportId, deleteItem ->
                MyDeleteHistoryDialog(requireContext()) {
                    myViewModel.deleteReport(reportId)
                    deleteItem()
                }.show()
//                myViewModel.deleteReport(reportId)
            },
            onItemClick = { reportId, tag, name ->
                when (tag) {
                    "실종신고" -> {
                        findNavController().navigate(
                            MyReportHistoryFragmentDirections.actionFragmentMyReportHistoryToFragmentSearchDetailDisappear(
                                id = reportId.toString(), tag = tag, name = name
                            )
                        )
                    }

                    "목격신고" -> {
                        findNavController().navigate(
                            MyReportHistoryFragmentDirections.actionFragmentMyReportHistoryToFragmentSearchDetailWitness(
                                id = reportId.toString(), tag = tag, name = name
                            )
                        )
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyReportHistoryBinding.inflate(inflater, container, false)

        initListener()
        myViewModel.fetchReportHistory()

        return binding.root
    }

    private fun initListener() {
        binding.clMyReportHistoryBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.currentState) {
                launch {
                    myViewModel.reportHistory.collectLatest { reportHistory ->
                        myReportHistoryAdapter.submitList(reportHistory)
                        binding.rvMyReportHistory.smoothScrollToPosition(0)
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        with(binding.rvMyReportHistory) {
            adapter = myReportHistoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}