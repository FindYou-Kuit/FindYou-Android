package com.example.findu.presentation.ui.report.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemBreedsBinding

class ReportBreedAdapter: ListAdapter<String, RecyclerView.ViewHolder>(diffUtil) {

    inner class BreedViewHolder(private val binding: ItemBreedsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: String) {
            binding.btnBreed.text = breed
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BreedViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBreedsBinding.inflate(inflater, parent, false)
        return BreedViewHolder(binding)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}