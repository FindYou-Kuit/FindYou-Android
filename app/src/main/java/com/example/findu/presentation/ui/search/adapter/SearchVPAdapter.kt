package com.example.findu.presentation.ui.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.findu.presentation.ui.home.HomeFragment
import com.example.findu.presentation.ui.search.SearchAllFragment
import com.example.findu.presentation.ui.search.SearchProtectingDetailFragment
import com.example.findu.presentation.ui.search.SearchReportFragment
import com.example.findu.presentation.ui.search.SearchRescueFragment

class SearchVPAdapter(fragmentActivity :FragmentActivity): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> SearchAllFragment()
           1-> SearchRescueFragment()
           2 -> SearchReportFragment()
           else -> throw IllegalArgumentException("Invalid position: $position")
       }
    }
}