package com.example.findu.presentation.ui.my

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.databinding.FragmentMyInterestBinding
import com.example.findu.presentation.model.MyInterestRv
import com.example.findu.presentation.ui.my.adapter.MyInterestRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyInterestFragment : Fragment() {
    private var _binding: FragmentMyInterestBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()
    private val myInterestRvAdapter = MyInterestRvAdapter(
        onKeepClick = { animalId, interest, tag ->
            myViewModel.setInterest(
                id = animalId,
                isInterest = interest,
            )
        },
        onItemClick = { animalId, tag, name ->
            when (tag) {
                "실종신고" -> {
                    findNavController().navigate(
                        MyInterestFragmentDirections.actionFragmentMyKeepAnimalsToFragmentSearchDetailDisappear( // 실종
                            id = animalId.toString(), tag = tag, name = name
                        )
                    )
                }

                "보호중" -> {
                    findNavController().navigate(
                        MyInterestFragmentDirections.actionFragmentMyKeepAnimalsToFragmentSearchDetailProtecting( // 보호
                            id = animalId.toString(), tag = tag, name = name
                        )
                    )
                }

                "목격신고" -> {
                    findNavController().navigate(
                        MyInterestFragmentDirections.actionFragmentMyKeepAnimalsToFragmentSearchDetailWitness( // 목격
                            id = animalId.toString(), tag = tag, name = name
                        )
                    )
                }
            }
        }
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyInterestBinding.inflate(inflater, container, false)

        initListener()
        myViewModel.fetchInterestAnimals()

        return binding.root
    }

    private fun initListener() {
        binding.clMyKeepAnimalsBack.setOnClickListener {
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
                    myViewModel.interestAnimals.collectLatest { data ->
                        myInterestRvAdapter.submitList(data)
                        binding.rvMyKeepAnimals.scrollToPosition(0)
                        binding.rvMyKeepAnimals.smoothScrollToPosition(0)
                    }
                }

                launch {
                    myViewModel.errorMessage.collectLatest { message ->
                        message?.let {
                            Log.e("MyInterestFragment", it)
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        with(binding.rvMyKeepAnimals) {
            adapter = myInterestRvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}