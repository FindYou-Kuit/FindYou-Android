package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.SearchDetailImgItemBinding
import com.example.findu.presentation.ui.search.model.SearchDetailData

class SearchDetailVPAdapter(private val images: List<SearchDetailData>) :
    RecyclerView.Adapter<SearchDetailVPAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: SearchDetailImgItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageData: SearchDetailData) {
            binding.ivSearchDetailContent.setImageResource(imageData.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchDetailImgItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
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