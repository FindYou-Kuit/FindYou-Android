package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemSearchDetailImgBinding
import com.example.findu.presentation.ui.search.model.DetailSearchRv

class SearchDetailVPAdapter(private val images:List<String>) :
    RecyclerView.Adapter<SearchDetailVPAdapter.ViewHolder>() {

    private val extendedImg: List<String> = if (images.isNotEmpty()) {
        listOf(images.last()) + images + listOf(images.first())
    } else {
        listOf("default_image_url")
    }
    inner class ViewHolder(private val binding: ItemSearchDetailImgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.root.context)
                .load(imageUrl)
                .into(binding.ivSearchDetailContent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchDetailImgBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return extendedImg.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(extendedImg[position])
    }
}