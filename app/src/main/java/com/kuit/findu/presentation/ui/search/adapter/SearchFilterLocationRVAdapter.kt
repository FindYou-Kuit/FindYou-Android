package com.kuit.findu.presentation.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kuit.findu.R
import com.kuit.findu.databinding.ItemSearchLocationBinding

class SearchFilterLocationRVAdapter(
    private val locations: List<String>,
    private var selectedLocation: String?,
    private val onLocationSelected: (String) -> Unit
) : RecyclerView.Adapter<SearchFilterLocationRVAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding =
            ItemSearchLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location, location == selectedLocation)
    }

    override fun getItemCount(): Int = locations.size

    inner class LocationViewHolder(private val binding: ItemSearchLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(location: String, isSelected: Boolean) {
            with(binding) {
                tvLocationName.text = location
                if (isSelected) {
                    tvLocationName.setTextColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.main_color
                        )
                    )
                    tvLocationName.setTextAppearance(R.style.TextAppearance_FindU_Body2_SB_14)
                    tvLocationName.setTextColor(ContextCompat.getColor(root.context, R.color.main_color))
                } else {
                    tvLocationName.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))
                    tvLocationName.setTextAppearance(R.style.TextAppearance_FindU_Body2_R_14)
                }
                root.setOnClickListener {
                    if (selectedLocation != location) {
                        selectedLocation = location
                        onLocationSelected(location)
                        notifyDataSetChanged()
                    }
                }
            }


        }
    }
}