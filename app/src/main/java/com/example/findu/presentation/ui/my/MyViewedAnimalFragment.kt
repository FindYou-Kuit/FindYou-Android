package com.example.findu.presentation.ui.my

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
import com.example.findu.databinding.FragmentMyViewedAnimalBinding
import com.example.findu.presentation.ui.my.adapter.MyViewedAnimalsRvAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyViewedAnimalFragment : Fragment() {
    private var _binding: FragmentMyViewedAnimalBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    private val myRecentHistoryRvAdapter = MyViewedAnimalsRvAdapter(
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
        _binding = FragmentMyViewedAnimalBinding.inflate(inflater, container, false)

        initListener()
        myViewModel.fetchViewedAnimals()

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
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycle.currentState) {
                launch {
                    myViewModel.viewedAnimals.collectLatest { viewedAnimals ->
                        myRecentHistoryRvAdapter.submitList(viewedAnimals)
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        with(binding.rvMyRecentHistory) {
            adapter = myRecentHistoryRvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}