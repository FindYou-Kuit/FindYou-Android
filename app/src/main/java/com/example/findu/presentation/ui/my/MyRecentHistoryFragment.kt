package com.example.findu.presentation.ui.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.databinding.FragmentMyRecentHistoryBinding
import com.example.findu.presentation.ui.my.adapter.MyRecentHistoryRvAdapter

class MyRecentHistoryFragment : Fragment() {
    private var _binding: FragmentMyRecentHistoryBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    private val myRecentHistoryRvAdapter = MyRecentHistoryRvAdapter(
        onKeepClick = { cardId, interest ->
//            myViewModel.patchInterest(cardId, interest)
        },
        onItemClick = {
//            myViewModel.getDetail(it)
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRecentHistoryBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.clMyRecentHistoryBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
    }

    private fun setUpAdapter() {
        with(binding.rvMyRecentHistory) {
            adapter = myRecentHistoryRvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}