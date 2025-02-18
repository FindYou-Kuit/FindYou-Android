package com.example.findu.presentation.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.findu.databinding.FragmentMyBinding

class MyFragment : Fragment() {
    private var _binding: FragmentMyBinding? = null
    private val binding get() = _binding!!
    private val myViewModel by viewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBinding.inflate(inflater, container, false)


        initListener()

        return binding.root
    }

    private fun initListener() {
        binding.llMyNickname.setOnClickListener {
            binding.llMyNickname.visibility = View.INVISIBLE
            binding.llMyEditNickname.visibility = View.VISIBLE
        }

        binding.btnMyDoneEdit.setOnClickListener {
            binding.llMyNickname.visibility = View.VISIBLE
            binding.llMyEditNickname.visibility = View.INVISIBLE

            // patch nickname api
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}