package com.example.findu

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.example.findu.databinding.FragmentSearchFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding : FragmentSearchFilterBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSearchFilterBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }


}