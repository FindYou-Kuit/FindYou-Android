package com.example.findu.presentation.ui.report.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemFeatureChipBinding

class ReportFeatureAdapter(
    private val features: List<String>
) : RecyclerView.Adapter<ReportFeatureAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFeatureChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feature: String) {
            binding.chipReportFeature.text = feature
            binding.root.setOnClickListener {
                binding.chipReportFeature.isChecked = !binding.chipReportFeature.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFeatureChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(features[position])
    }

    override fun getItemCount() = features.size

}