package com.example.findu.presentation.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.databinding.ItemSearchBreedsBinding

class SearchFilterBreedRVAdapter(
    private val breeds: List<String>,
    private val selectedBreeds: MutableList<String>,
    private val onBreedSelected: (List<String>) -> Unit
) : RecyclerView.Adapter<SearchFilterBreedRVAdapter.BreedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemSearchBreedsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed = breeds[position]
        holder.bind(breed, selectedBreeds.contains(breed))
    }

    override fun getItemCount(): Int = breeds.size

    inner class BreedViewHolder(private val binding: ItemSearchBreedsBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(breed: String, isSelected: Boolean) {
            with(binding){
                tvBreedName.text = breed
                if (isSelected) {
                    tvBreedName.setTextColor(ContextCompat.getColor(root.context, R.color.main_color))
                    tvBreedName.setTextAppearance(R.style.TextAppearance_FindU_Tag1_SB_12)
                } else {
                    tvBreedName.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))
                    tvBreedName.setTextAppearance(R.style.TextAppearance_FindU_caption_12)
                }
                root.setOnClickListener {
                    if (selectedBreeds.contains(breed)) {
                        selectedBreeds.remove(breed)
                    } else {
                        selectedBreeds.add(breed)
                    }
                    onBreedSelected(selectedBreeds)
                    notifyDataSetChanged()
                }
            }

        }
    }
}