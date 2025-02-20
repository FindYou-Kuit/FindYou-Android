package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.R
import com.example.findu.databinding.SearchHorizontalContentItemBinding
import com.example.findu.databinding.ItemSearchGridContentBinding
import com.example.findu.presentation.ui.search.model.SearchRv

class SearchContentRVAdapter(
    private var items: List<SearchRv>,
    private val onItemClick: (SearchRv) -> Unit,
    private val onBookmarkClick: (Long, Boolean, String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HORIZONTAL = 0
        const val VIEW_TYPE_GRID = 1
    }

    private var isGridMode = false

    fun setGridMode(enabled: Boolean) {
        isGridMode = enabled
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridMode) VIEW_TYPE_GRID else VIEW_TYPE_HORIZONTAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_GRID -> {
                val binding = ItemSearchGridContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                GridViewHolder(binding)
            }

            else -> {
                val binding = SearchHorizontalContentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HorizontalViewHolder(binding)
            }
        }
    }

    fun returnItemSize(): Int {
        return items.size
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is GridViewHolder -> holder.bind(item)
            is HorizontalViewHolder -> holder.bind(item)
        }
    }

    fun updateData(newItems: List<SearchRv>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = newItems.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].name == newItems[newItemPosition].name
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newItems[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HorizontalViewHolder(private val binding: SearchHorizontalContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchRv) {

            initView(item)
            initListener(item)
        }

        private fun initListener(item: SearchRv) {
            with(binding) {
                root.setOnClickListener { onItemClick(item) }
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    onBookmarkClick(item.cardId, item.isBookmark, item.tag.text)
                    if (item.isBookmark) {
                        binding.ivSearchContentBookmark.setImageResource(R.drawable.ic_search_fill_bookmark)
                    } else {
                        binding.ivSearchContentBookmark.setImageResource(R.drawable.ic_search_blank_bookmark)
                    }
                }
            }
        }

        private fun initView(item: SearchRv) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                tvSearchContentStatus.text = item.tag.text
                tvSearchContentStatus.setTextColor(
                    binding.root.context.getColor(item.tag.textColor)
                )
                tvSearchContentStatus.setBackgroundResource(item.tag.backgroundRes)
                if (item.isBookmark) {
                    binding.ivSearchContentBookmark.setImageResource(R.drawable.ic_search_fill_bookmark)
                } else {
                    binding.ivSearchContentBookmark.setImageResource(R.drawable.ic_search_blank_bookmark)
                }
                Glide.with(binding.root.context)
                    .load(item.image)
                    .into(binding.ivSearchContent)
            }
        }
    }

    inner class GridViewHolder(private val binding: ItemSearchGridContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchRv) {
            binding.tvSearchContentName.text = item.name
            binding.tvSearchContentDate.text = item.date
            binding.tvSearchContentAddress.text = item.address
            binding.tvSearchContentStatus.text = item.tag.text

            binding.tvSearchContentStatus.setTextColor(
                binding.root.context.getColor(item.tag.textColor)
            )
            binding.tvSearchContentStatus.setBackgroundResource(item.tag.backgroundRes)

            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.ivSearchContent)
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}