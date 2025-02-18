package com.example.findu.presentation.ui.info

import android.content.Intent
import android.net.Uri
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

        binding.llVolunteerInfoApplication.setOnClickListener {
            val url = "https://www.1365.go.kr/vols/search.do?query=%EC%9C%A0%EA%B8%B0%EB%8F%99%EB%AC%BC+%EB%B3%B4%ED%98%B8%EC%86%8C"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}