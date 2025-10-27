package com.kuit.findu.presentation.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kuit.findu.R
import com.kuit.findu.databinding.ItemSearchBreedsBinding

class SearchBreedRVAdapter(
    private val allItems: List<String>,
    private val isSelected: (String) -> Boolean,
    private val onPick: (String) -> Unit,
) : RecyclerView.Adapter<SearchBreedRVAdapter.ViewHolder>(), Filterable {

    private var visibleItems = allItems.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val b = ItemSearchBreedsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(b)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = visibleItems[position]
        holder.bind(item, isSelected(item))
    }

    override fun getItemCount(): Int = visibleItems.size

    inner class ViewHolder(private val b: ItemSearchBreedsBinding) :
        RecyclerView.ViewHolder(b.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(text: String, selectedNow: Boolean) = with(b) {
            tvBreedName.text = text

            if (selectedNow) {
                tvBreedName.setTextAppearance(R.style.TextAppearance_FindU_Body2_SB_14)
                tvBreedName.setTextColor(ContextCompat.getColor(root.context, R.color.main_color))
            } else {
                tvBreedName.setTextAppearance(R.style.TextAppearance_FindU_Body2_R_14)
                tvBreedName.setTextColor(ContextCompat.getColor(root.context, R.color.gray6))
            }

            root.setOnClickListener {
                onPick(text)
                notifyDataSetChanged()
            }
        }
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(cs: CharSequence?): FilterResults {
            val q = cs?.toString()?.trim()?.lowercase().orEmpty()
            val list = if (q.isEmpty()) allItems else allItems.filter { it.lowercase().contains(q) }
            return FilterResults().apply { values = list }
        }

        @SuppressLint("NotifyDataSetChanged")
        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            visibleItems = (results?.values as? List<String>)?.toMutableList() ?: mutableListOf()
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshSelections() {
        notifyDataSetChanged()
    }
}