package com.example.findu.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemHomeRvBinding
import com.example.findu.presentation.model.HomeRv
import com.example.findu.presentation.type.AnimalStateType

class HomeRVAdapter(
    private val items: List<HomeRv>
) : RecyclerView.Adapter<HomeRVAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(private val binding: ItemHomeRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeRv) {
            binding.tvHomeName.text = item.name
            binding.tvHomeDate.text = item.date
            binding.tvHomeLocation.text = item.location

            binding.chipHomeAnimalState.text = item.type
            val stateType = AnimalStateType.entries.first { it.state == item.type }
            binding.chipHomeAnimalState.setTextColor(
                binding.root.context.getColor(stateType.textColor)
            )
            binding.chipHomeAnimalState.chipBackgroundColor =
                binding.root.context.getColorStateList(stateType.backgroundChipColor)


            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.ivHome)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =
            ItemHomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}