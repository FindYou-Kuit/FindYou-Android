package com.example.findu.presentation.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentInfoBinding
import com.example.findu.presentation.model.InfoRv

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private val infoViewModel by viewModels<InfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        setupRV()

        return binding.root
    }

    private fun setupRV() {
        lateinit var infoAdapter: InfoRvAdapter
        val infoList: List<InfoRv> = listOf(
            InfoRv(
                image = R.drawable.img_banner_green,
                title = R.string.info_title,
                description = R.string.info_description
            ),
            InfoRv(
                image = R.drawable.img_banner_purple,
                title = R.string.info_title,
                description = R.string.info_description
            ),
            InfoRv(
                image = R.drawable.img_banner_blue,
                title = R.string.info_title,
                description = R.string.info_description
            )
        )
        infoAdapter = InfoRvAdapter(infoList)
        binding.rvInfo.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvInfo.adapter = infoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}