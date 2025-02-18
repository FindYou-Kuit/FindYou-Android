package com.example.findu.presentation.ui.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findu.databinding.FragmentMyKeepAnimalsBinding

class MyKeepAnimalsFragment : Fragment() {
    private var _binding: FragmentMyKeepAnimalsBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyKeepAnimalsBinding.inflate(inflater, container, false)

        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.clMyKeepAnimalsBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}