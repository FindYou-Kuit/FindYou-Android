package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.R
import com.example.findu.databinding.ItemSearchGridContentBinding
import com.example.findu.databinding.ItemSearchHeaderBinding
import com.example.findu.databinding.SearchHorizontalContentItemBinding
import com.example.findu.presentation.ui.search.model.SearchRv

sealed class SearchListItem {
    data object Header : SearchListItem()
    data class Content(val data: SearchRv) : SearchListItem()
}

class SearchListAdapter(
    private val onFilterClick: () -> Unit,
    private val onToggleClick: () -> Unit,
    private val onItemClick: (SearchRv) -> Unit,
    private val onBookmarkClick: (Long, Boolean, String) -> Unit
) : ListAdapter<SearchListItem, RecyclerView.ViewHolder>(DIFF) {

    companion object {
        const val VIEW_TYPE_HEADER = 999
        const val VIEW_TYPE_HORIZONTAL = 0
        const val VIEW_TYPE_GRID = 1

        private val DIFF = object : DiffUtil.ItemCallback<SearchListItem>() {
            override fun areItemsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean {
                return when {
                    oldItem is SearchListItem.Header && newItem is SearchListItem.Header -> true
                    oldItem is SearchListItem.Content && newItem is SearchListItem.Content ->
                        oldItem.data.cardId == newItem.data.cardId
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var isGridMode = false
    fun setGridMode(enabled: Boolean) {
        if (isGridMode == enabled) return
        isGridMode = enabled
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is SearchListItem.Header -> VIEW_TYPE_HEADER
            is SearchListItem.Content -> if (isGridMode) VIEW_TYPE_GRID else VIEW_TYPE_HORIZONTAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ItemSearchHeaderBinding.inflate(inflater, parent, false)
                HeaderVH(binding)
            }
            VIEW_TYPE_GRID -> {
                val binding = ItemSearchGridContentBinding.inflate(inflater, parent, false)
                GridVH(binding)
            }
            else -> {
                val binding = SearchHorizontalContentItemBinding.inflate(inflater, parent, false)
                HorizontalVH(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderVH -> holder.bind()
            is GridVH -> holder.bind((getItem(position) as SearchListItem.Content).data)
            is HorizontalVH -> holder.bind((getItem(position) as SearchListItem.Content).data)
        }
    }

    inner class HeaderVH(private val binding: ItemSearchHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.ibSearchFilter.setOnClickListener { onFilterClick() }
            binding.ibSearchHorizontalSort.setOnClickListener { onToggleClick() }
        }
    }

    inner class HorizontalVH(private val binding: SearchHorizontalContentItemBinding) :
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

                Glide.with(root.context).load(item.image).into(ivSearchContent)

                root.setOnClickListener { onItemClick(item) }
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    onBookmarkClick(item.cardId, item.isBookmark, item.tag.text)
                    updateBookmarkIcon(item.isBookmark)
                }
            }
        }

        private fun updateBookmarkIcon(isBookmarked: Boolean) {
            binding.ivSearchContentBookmark.setImageResource(
                if (isBookmarked) R.drawable.ic_search_fill_bookmark
                else R.drawable.ic_search_blank_bookmark
            )
        }
    }

    inner class GridVH(private val binding: ItemSearchGridContentBinding) :
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

                Glide.with(root.context).load(item.image).into(ivSearchContent)

                root.setOnClickListener { onItemClick(item) }
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    onBookmarkClick(item.cardId, item.isBookmark, item.tag.text)
                    updateBookmarkIcon(item.isBookmark)
                }
            }
        }

        private fun updateBookmarkIcon(isBookmarked: Boolean) {
            binding.ivSearchContentBookmark.setImageResource(
                if (isBookmarked) R.drawable.ic_search_fill_bookmark
                else R.drawable.ic_search_blank_bookmark
            )
        }
    }

    fun submitContent(list: List<SearchRv>) {
        val display = buildList {
            add(SearchListItem.Header)
            addAll(list.map { SearchListItem.Content(it) })
        }
        submitList(display)
    }

    fun addContent(list: List<SearchRv>) {
        val base = currentList.toMutableList()
        if (base.isEmpty() || base.first() !is SearchListItem.Header) {
            base.add(0, SearchListItem.Header)
        }
        base.addAll(list.map { SearchListItem.Content(it) })
        submitList(base)
    }
}