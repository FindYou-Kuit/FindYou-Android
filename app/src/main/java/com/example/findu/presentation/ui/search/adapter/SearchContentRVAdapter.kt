package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.R
import com.example.findu.databinding.SearchHorizontalContentItemBinding
import com.example.findu.databinding.ItemSearchGridContentBinding
import com.example.findu.presentation.ui.search.model.SearchRv

class SearchContentRVAdapter(
    private val onItemClick: (SearchRv) -> Unit,
    private val onBookmarkClick: (Long, Boolean, String) -> Unit
) : ListAdapter<SearchRv, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        const val VIEW_TYPE_HORIZONTAL = 0
        const val VIEW_TYPE_GRID = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchRv>() {
            override fun areItemsTheSame(oldItem: SearchRv, newItem: SearchRv): Boolean {
                return oldItem.cardId == newItem.cardId
            }

            override fun areContentsTheSame(oldItem: SearchRv, newItem: SearchRv): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var isGridMode = false

    fun setGridMode(enabled: Boolean) {
        isGridMode = enabled
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGridMode) VIEW_TYPE_GRID else VIEW_TYPE_HORIZONTAL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_GRID -> {
                val binding = ItemSearchGridContentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                GridViewHolder(binding)
            }

            else -> {
                val binding = SearchHorizontalContentItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                HorizontalViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is GridViewHolder -> holder.bind(item)
            is HorizontalViewHolder -> holder.bind(item)
        }
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
                    updateBookmarkIcon(item.isBookmark)
                }
            }
        }

        private fun initView(item: SearchRv) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                tvSearchContentStatus.text = item.tag.text
                tvSearchContentStatus.setTextColor(root.context.getColor(item.tag.textColor))
                tvSearchContentStatus.setBackgroundResource(item.tag.backgroundRes)
                updateBookmarkIcon(item.isBookmark)

                Glide.with(root.context)
                    .load(item.image)
                    .into(ivSearchContent)
            }
        }

        private fun updateBookmarkIcon(isBookmarked: Boolean) {
            binding.ivSearchContentBookmark.setImageResource(
                if (isBookmarked) R.drawable.ic_search_fill_bookmark else R.drawable.ic_search_blank_bookmark
            )
        }
    }

    inner class GridViewHolder(private val binding: ItemSearchGridContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchRv) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                tvSearchContentStatus.text = item.tag.text
                tvSearchContentStatus.setTextColor(root.context.getColor(item.tag.textColor))
                tvSearchContentStatus.setBackgroundResource(item.tag.backgroundRes)
                updateBookmarkIcon(item.isBookmark)

                Glide.with(root.context)
                    .load(item.image)
                    .into(ivSearchContent)

                root.setOnClickListener { onItemClick(item) }
            }
        }

        private fun updateBookmarkIcon(isBookmarked: Boolean) {
            binding.ivSearchContentBookmark.setImageResource(
                if (isBookmarked) R.drawable.ic_search_fill_bookmark else R.drawable.ic_search_blank_bookmark
            )
        }
    }

    fun addData(list: List<SearchRv>) {
        val currentList = currentList.toMutableList()
        currentList.addAll(list)
        submitList(currentList)
    }
}