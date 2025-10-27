package com.kuit.findu.presentation.ui.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuit.findu.presentation.ui.search.tablayout.SearchAllFragment
import com.kuit.findu.presentation.ui.search.tablayout.SearchReportFragment
import com.kuit.findu.presentation.ui.search.tablayout.SearchRescueFragment

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