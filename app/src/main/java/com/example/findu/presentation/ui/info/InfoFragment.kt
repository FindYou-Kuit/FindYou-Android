package com.example.findu.presentation.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findu.R
import com.example.findu.databinding.FragmentInfoBinding
import com.example.findu.presentation.model.InfoRv

class InfoFragment : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

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
                image = R.drawable.img_report_info,
                title = R.string.info_report_title,
                description = R.string.report_info_description
            ),
            InfoRv(
                image = R.drawable.img_adopt_info,
                title = R.string.info_adopt_title,
                description = R.string.adopt_info_description
            ),
            InfoRv(
                image = R.drawable.img_volunteer_info,
                title = R.string.info_volunteer_title,
                description = R.string.volunteer_info_description
            )
        )
        infoAdapter = InfoRvAdapter(infoList) { item ->
            when (item.image) {
                R.drawable.img_report_info -> {
                    findNavController().navigate(R.id.fragment_report_info)
                }

                R.drawable.img_adopt_info -> {
                    findNavController().navigate(R.id.fragment_adopt_info)
                }

                R.drawable.img_volunteer_info -> {
                    findNavController().navigate(R.id.fragment_volunteer_info)
                }
            }
        }
        binding.rvInfo.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvInfo.adapter = infoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}