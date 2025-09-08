package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemSearchHeaderBinding
import com.example.findu.presentation.ui.search.tablayout.SearchAllFragment

class SearchHeaderAdapter(
    private val onFilterClick: () -> Unit,
    private val onToggleClick: () -> Unit
) : RecyclerView.Adapter<SearchHeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val binding = ItemSearchHeaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HeaderViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return SearchAllFragment.HEADER_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    inner class HeaderViewHolder(private val binding: ItemSearchHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.ibSearchFilter.setOnClickListener { onFilterClick() }
            binding.ibSearchHorizontalSort.setOnClickListener { onToggleClick() }
        }
    }
}