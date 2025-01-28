package com.example.findu.presentation.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R

class SearchFilterLocationRVAdapter(
    private val locations: List<String>,
    private var selectedLocation: String?,
    private val onLocationSelected: (String) -> Unit
) : RecyclerView.Adapter<SearchFilterLocationRVAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location, location == selectedLocation)
    }

    override fun getItemCount(): Int = locations.size

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationText: TextView = itemView.findViewById(R.id.tv_location_name)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(location: String, isSelected: Boolean) {
            locationText.text = location

            if (isSelected) {
                locationText.setTextColor(ContextCompat.getColor(itemView.context, R.color.main_color))
                locationText.setTextAppearance(R.style.TextAppearance_FindU_Tag1_SB_12)
            } else {
                locationText.setTextColor(ContextCompat.getColor(itemView.context, R.color.gray6))
                locationText.setTextAppearance(R.style.TextAppearance_FindU_caption_12)
            }

            itemView.setOnClickListener {
                if (selectedLocation != location) {
                    selectedLocation = location
                    onLocationSelected(location)
                    notifyDataSetChanged()
                }
            }
        }
    }
}