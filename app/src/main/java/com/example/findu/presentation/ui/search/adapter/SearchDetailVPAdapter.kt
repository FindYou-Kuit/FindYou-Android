package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemSearchDetailImgBinding
import com.example.findu.presentation.ui.search.model.DetailSearchRv

class SearchDetailVPAdapter(private val images:List<Any>) :
    RecyclerView.Adapter<SearchDetailVPAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemSearchDetailImgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Any) {
            when (item) {
                is String -> {
                    Glide.with(binding.root)
                        .load(item)
                        .into(binding.ivSearchDetailContent)
                }
                is Int -> {
                    binding.ivSearchDetailContent.setImageResource(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchDetailImgBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }
}