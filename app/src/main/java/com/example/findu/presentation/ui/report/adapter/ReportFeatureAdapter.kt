package com.example.findu.presentation.ui.report.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemFeatureChipBinding
import com.example.findu.presentation.type.report.ReportFeature

class ReportFeatureAdapter(
    private val features: List<ReportFeature>,
    private val onFeatureClick: (Int) -> Unit
) : RecyclerView.Adapter<ReportFeatureAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFeatureChipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(feature: ReportFeature) {
            binding.root.setOnClickListener {
                binding.chipReportFeature.isChecked = !binding.chipReportFeature.isChecked
            }
            with(binding.chipReportFeature) {
                text = feature.feature
                setOnCheckedChangeListener { _, _ ->
                    onFeatureClick(feature.featureId)
                }
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