package com.example.findu.presentation.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.findu.databinding.FragmentVolunteerInfoBinding

class VolunteerInfoFragment : Fragment() {
    private var _binding: FragmentVolunteerInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVolunteerInfoBinding.inflate(inflater, container, false)
        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.ivVolunteerInfoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}