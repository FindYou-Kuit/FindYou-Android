package com.example.findu.presentation.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R

class SearchFilterBreedRVAdapter(
    private val breeds: List<String>,
    private val selectedBreeds: MutableList<String>,
    private val onBreedSelected: (List<String>) -> Unit
) : RecyclerView.Adapter<SearchFilterBreedRVAdapter.BreedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_breeds_item, parent, false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed = breeds[position]
        holder.bind(breed, selectedBreeds.contains(breed))
    }

    override fun getItemCount(): Int = breeds.size

    inner class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val breedText: TextView = itemView.findViewById(R.id.tv_breed_name)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(breed: String, isSelected: Boolean) {
            breedText.text = breed
            if (isSelected) {
                breedText.setTextColor(ContextCompat.getColor(itemView.context, R.color.main_color))
                breedText.setTextAppearance(R.style.TextAppearance_FindU_Tag1_SB_12)
            } else {
                breedText.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray6))
                breedText.setTextAppearance(R.style.TextAppearance_FindU_caption_12)
            }

            itemView.setOnClickListener {
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