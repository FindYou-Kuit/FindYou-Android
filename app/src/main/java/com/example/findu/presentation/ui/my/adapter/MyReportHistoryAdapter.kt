package com.example.findu.presentation.ui.my.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemMyHistoryBinding
import com.example.findu.presentation.model.MyReportHistoryRv
import com.example.findu.presentation.type.AnimalStateType
import com.example.findu.presentation.util.ViewUtils.addUnderLine

class MyReportHistoryAdapter(
    private val onDeleteClick: (Long, () -> Unit) -> Unit,
    private val onItemClick: (Long) -> Unit = {}
) : ListAdapter<MyReportHistoryRv, MyReportHistoryAdapter.MyListRvViewHolder>(diffUtil) {

    inner class MyListRvViewHolder(private val binding: ItemMyHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyReportHistoryRv) {
            setUpView(item)
            initListener(item)

            Glide.with(binding.root.context)
                .load(item.thumbnailImageUrl)
                .into(binding.ivMyHistoryImage)
        }

        private fun setUpView(item: MyReportHistoryRv) {
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

                flMyHistoryKeep.visibility = View.GONE

                tvMyHistoryDelete.addUnderLine()
            }
        }

        private fun initListener(item: MyReportHistoryRv) {
            with(binding) {
                tvMyHistoryDelete.setOnClickListener {
                    onDeleteClick(item.reportId) {
                        deleteItem(item.reportId)
                    }
                }
                clMyHistoryContainer.setOnClickListener {
                    onItemClick(item.reportId)
                }
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

    private fun deleteItem(reportId: Long) {
        val position = currentList.indexOfFirst { it.reportId == reportId }
        if (position != -1) {
            val list = currentList.toMutableList()
            list.removeAt(position)
            submitList(list)
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyReportHistoryRv>() {

            override fun areItemsTheSame(
                oldItem: MyReportHistoryRv,
                newItem: MyReportHistoryRv
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MyReportHistoryRv,
                newItem: MyReportHistoryRv
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}