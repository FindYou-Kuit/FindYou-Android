package com.example.findu.presentation.ui.report.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemReportColorsBinding
import com.example.findu.domain.model.report.FurColorType

class ReportColorAdapter() : RecyclerView.Adapter<ReportColorAdapter.ViewHolder>() {

    private val colorList = FurColorType.entries.toList()
    private val selectedColorList = mutableListOf<FurColorType>()

    inner class ViewHolder(private val binding: ItemReportColorsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(furColor: FurColorType) {
            binding.tvReportColorsName.text = furColor.color
            binding.cbReportColors.isChecked = selectedColorList.contains(furColor)
            binding.root.setOnClickListener {
                binding.cbReportColors.isChecked = !binding.cbReportColors.isChecked
                if (binding.cbReportColors.isChecked) {
                    selectedColorList.add(furColor)
                } else {
                    selectedColorList.remove(furColor)
                }
            }
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
        holder.bind(colorList[position])
    }

    override fun getItemCount(): Int = colorList.size

    fun updateSelectedColors(newSelectedColors: List<FurColorType>) {
        selectedColorList.clear()
        selectedColorList.addAll(newSelectedColors)
        notifyDataSetChanged()
    }
}