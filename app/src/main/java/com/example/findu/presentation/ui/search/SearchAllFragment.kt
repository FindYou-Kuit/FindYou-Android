package com.example.findu.presentation.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findu.databinding.FragmentSearchAllBinding

class SearchAllFragment : Fragment() {

    lateinit var binding : FragmentSearchAllBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchAllBinding.inflate(layoutInflater)

        return binding.root
    }


}