package com.example.findu.presentation.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemInfoRvBinding
import com.example.findu.presentation.model.InfoRv

class InfoRvAdapter(
    private val items: List<InfoRv>
) : RecyclerView.Adapter<InfoRvAdapter.InfoViewHolder>() {
    inner class InfoViewHolder(private val binding: ItemInfoRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InfoRv) {
            binding.tvInfoTitle.text = binding.root.context.getString(item.title)
            binding.tvInfoDescription.text = binding.root.context.getString(item.description)

            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.ivInfoImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding = ItemInfoRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}