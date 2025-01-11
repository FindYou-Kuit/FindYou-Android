package com.example.findu.presentation.ui.report.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemReportColorsBinding
import com.example.findu.presentation.type.report.FurColorType

class ReportColorAdapter : ListAdapter<FurColorType, ReportColorAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemReportColorsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: FurColorType) {
            binding.tvReportColorsName.text = color.color
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReportColorsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<FurColorType>() {

            override fun areItemsTheSame(
                oldItem: FurColorType,
                newItem: FurColorType
            ): Boolean {
                return oldItem.color == newItem.color
            }

            override fun areContentsTheSame(
                oldItem: FurColorType,
                newItem: FurColorType
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}