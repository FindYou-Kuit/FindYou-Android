package com.example.findu.presentation.ui.home.adapter

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
        fun bind(imageUrl: String, position: Int, totalCount: Int) {
            Glide.with(binding.root)
                .load(imageUrl)
                .into(binding.ivHomeVpImg)

            binding.tvHomeBannerNum.text = String.format("%d/%d +", position + 1, totalCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeVpBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = position % imageUrls.size
        holder.bind(imageUrls[currentPosition], currentPosition, imageUrls.size)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}