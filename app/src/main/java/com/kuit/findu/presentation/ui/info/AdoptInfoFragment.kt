package com.kuit.findu.presentation.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kuit.findu.databinding.FragmentAdoptInfoBinding

class AdoptInfoFragment : Fragment() {
    private var _binding: FragmentAdoptInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdoptInfoBinding.inflate(inflater, container, false)

        initListener()
        return binding.root
    }

    private fun initListener() {
        binding.ivAdoptInfoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}