package com.example.findu.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.databinding.ItemHomeVpBannerBinding

class HomeBannerAdapter(
    private val imageUrls: List<Int>
) : RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHomeVpBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResId: Int) {
            binding.ivHomeVpImg.setImageResource(imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeVpBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = position % imageUrls.size
        holder.bind(imageUrls[currentPosition])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
}