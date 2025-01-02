package com.example.findu.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemHomeVpBannerBinding

class HomeBannerAdapter(
    private val imageUrls: List<String>
) : RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHomeVpBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            Glide.with(binding.root)
                .load(imageUrl)
                .into(binding.ivHomeVpImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeVpBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrls[position % imageUrls.size])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}