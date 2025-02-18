package com.example.findu.presentation.ui.my.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemMyHistoryBinding
import com.example.findu.presentation.model.MyRecentHistoryRv
import com.example.findu.presentation.model.MyReportHistoryRv
import com.example.findu.presentation.type.AnimalStateType

class MyRecentHistoryRvAdapter(
    private val onKeepClick: (Int, Boolean) -> Unit,
    private val onItemClick: (Int) -> Unit = {}
) : ListAdapter<MyRecentHistoryRv, MyRecentHistoryRvAdapter.MyListRvViewHolder>(diffUtil) {

    inner class MyListRvViewHolder(private val binding: ItemMyHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyRecentHistoryRv) {

            setUpView(item)
            initListener(item)

            Glide.with(binding.root.context)
                .load(item.thumbnailImageUrl)
                .into(binding.ivMyHistoryImage)
        }

        private fun setUpView(item: MyRecentHistoryRv) {
            with(binding) {
                tvMyHistoryTitle.text = item.title
                tvMyHistoryDate.text = item.date
                tvMyHistoryLocation.text = item.location

                chipMyHistoryAnimalState.text = item.tag
                val stateType = AnimalStateType.entries.first { it.state == item.tag }
                chipMyHistoryAnimalState.setTextColor(
                    binding.root.context.getColor(stateType.textColor)
                )
                chipMyHistoryAnimalState.chipBackgroundColor =
                    binding.root.context.getColorStateList(stateType.backgroundChipColor)

                tvMyHistoryDelete.visibility = View.GONE

                if (item.interest) {
                    binding.ivMyHistoryKeep.visibility = View.VISIBLE
                    binding.ivMyHistoryNonKeep.visibility = View.GONE
                } else {
                    binding.ivMyHistoryKeep.visibility = View.GONE
                    binding.ivMyHistoryNonKeep.visibility = View.VISIBLE
                }
            }
        }

        private fun initListener(item: MyRecentHistoryRv) {
            with(binding) {
                flMyHistoryKeep.setOnClickListener {
                    item.interest = !item.interest
                    onKeepClick(item.cardId, item.interest)
                    if (item.interest) {
                        binding.ivMyHistoryKeep.visibility = View.VISIBLE
                        binding.ivMyHistoryNonKeep.visibility = View.GONE
                    } else {
                        binding.ivMyHistoryKeep.visibility = View.GONE
                        binding.ivMyHistoryNonKeep.visibility = View.VISIBLE
                    }
                }
                clMyHistoryContainer.setOnClickListener { onItemClick(item.cardId) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListRvViewHolder {
        val binding =
            ItemMyHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyListRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyListRvViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyRecentHistoryRv>() {

            override fun areItemsTheSame(
                oldItem: MyRecentHistoryRv,
                newItem: MyRecentHistoryRv
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MyRecentHistoryRv,
                newItem: MyRecentHistoryRv
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}