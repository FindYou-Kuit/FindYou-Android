package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemSearchDetailImgBinding
import com.example.findu.presentation.ui.search.model.DetailSearchRv

class SearchDetailVPAdapter(private val images: List<DetailSearchRv>) :
    RecyclerView.Adapter<SearchDetailVPAdapter.ViewHolder>() {

    private val extendedImg = listOf(images.last()) + images + listOf(images.first())

    inner class ViewHolder(private val binding: ItemSearchDetailImgBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageData: DetailSearchRv) {
            binding.ivSearchDetailContent.setImageResource(imageData.image)
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

    fun getRealItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(extendedImg[position])
    }
}