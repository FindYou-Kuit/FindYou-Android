package com.example.findu.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.findu.R
import com.example.findu.databinding.FragmentSearchProtectingDetailBinding
import com.example.findu.presentation.ui.search.model.SearchDetailData
import com.example.findu.presentation.ui.search.adapter.SearchDetailVPAdapter

class SearchProtectingDetailFragment : Fragment() {
    private lateinit var binding: FragmentSearchProtectingDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchProtectingDetailBinding.inflate(layoutInflater)
        binding.ibSearchDetailBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack() // 이전 프래그먼트로
        }

        return binding.root
    }
}