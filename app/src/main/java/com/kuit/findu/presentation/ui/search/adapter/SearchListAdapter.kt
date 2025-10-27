package com.kuit.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kuit.findu.R
import com.kuit.findu.databinding.ItemSearchGridContentBinding
import com.kuit.findu.databinding.ItemSearchHeaderBinding
import com.kuit.findu.databinding.SearchHorizontalContentItemBinding
import com.kuit.findu.presentation.ui.search.model.SearchRv

sealed class SearchListItem {
    data object Header : SearchListItem()
    data class Content(val data: SearchRv) : SearchListItem()
}

class SearchListAdapter(
    private val listener: SearchListListener
) : ListAdapter<SearchListItem, SearchListAdapter.BaseVH>(DIFF) {

    companion object {
        const val VIEW_TYPE_HEADER = 999
        const val VIEW_TYPE_HORIZONTAL = 0
        const val VIEW_TYPE_GRID = 1

        private val DIFF = object : DiffUtil.ItemCallback<SearchListItem>() {
            override fun areItemsTheSame(oldItem: SearchListItem, newItem: SearchListItem): Boolean {
                return when {
                    oldItem is SearchListItem.Header && newItem is SearchListItem.Header -> true
                    oldItem is SearchListItem.Content && newItem is SearchListItem.Content ->
                        oldItem.data.reportId == newItem.data.reportId
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
        notifyItemRangeChanged(1, currentList.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is SearchListItem.Header -> VIEW_TYPE_HEADER
            is SearchListItem.Content -> if (isGridMode) VIEW_TYPE_GRID else VIEW_TYPE_HORIZONTAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> BaseVH.HeaderVH(
                ItemSearchHeaderBinding.inflate(inflater, parent, false),
                listener
            )
            VIEW_TYPE_GRID -> BaseVH.GridVH(
                ItemSearchGridContentBinding.inflate(inflater, parent, false),
                listener
            )
            else -> BaseVH.HorizontalVH(
                SearchHorizontalContentItemBinding.inflate(inflater, parent, false),
                listener
            )
        }
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        when (holder) {
            is BaseVH.HeaderVH -> holder.bind()
            is BaseVH.HorizontalVH -> holder.bind((getItem(position) as SearchListItem.Content).data)
            is BaseVH.GridVH -> holder.bind((getItem(position) as SearchListItem.Content).data)
        }
    }

    sealed class BaseVH(bindingRoot: ViewGroup) : RecyclerView.ViewHolder(bindingRoot) {
        class HeaderVH(
            private val binding: ItemSearchHeaderBinding,
            private val listener: SearchListListener
        ) : BaseVH(binding.root as ViewGroup) {
            private var isGrid = false
            fun bind() = with(binding) {
                ivSearchBanner.setImageResource(listener.getBannerRes())
                ibSearchFilter.setOnClickListener { listener.onFilterClick() }
                ibSearchHorizontalSort.setOnClickListener {
                    isGrid = !isGrid
                    val iconRes =
                        if (isGrid) R.drawable.ic_search_grid_sort else R.drawable.ic_search_horizontal_sort
                    ibSearchHorizontalSort.setImageResource(iconRes)
                    listener.onToggleClick()
                }
                ivSearchBanner.setOnClickListener { listener.onBannerClick() }
            }
        }

        class HorizontalVH(
            private val binding: SearchHorizontalContentItemBinding,
            private val listener: SearchListListener
        ) : BaseVH(binding.root as ViewGroup) {
            fun bind(item: SearchRv) = with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.location
                tvSearchContentStatus.text = item.tag.text
                tvSearchContentStatus.setTextColor(root.context.getColor(item.tag.textColor))
                tvSearchContentStatus.setBackgroundResource(item.tag.backgroundRes)
                updateBookmarkIcon(item.isBookmark)

                Glide.with(root.context)
                    .load(item.image.replace("http://", "https://"))
                    .centerCrop()
                    .transform(RoundedCorners(24))
                    .into(ivSearchContent)

                root.setOnClickListener { listener.onItemClick(item) }
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    listener.onBookmarkClick(item.reportId, item.isBookmark, item.tag.text)
                    updateBookmarkIcon(item.isBookmark)
                }
            }

            private fun updateBookmarkIcon(isBookmarked: Boolean) {
                binding.ivSearchContentBookmark.setImageResource(
                    if (isBookmarked) R.drawable.ic_search_fill_bookmark
                    else R.drawable.ic_search_blank_bookmark_horizontal
                )
            }
        }

        class GridVH(
            private val binding: ItemSearchGridContentBinding,
            private val listener: SearchListListener
        ) : BaseVH(binding.root as ViewGroup) {
            fun bind(item: SearchRv) = with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.location
                tvSearchContentStatus.text = item.tag.text
                tvSearchContentStatus.setTextColor(root.context.getColor(item.tag.textColor))
                tvSearchContentStatus.setBackgroundResource(item.tag.backgroundRes)
                updateBookmarkIcon(item.isBookmark)

                Glide.with(root.context)
                    .load(item.image.replace("http://", "https://"))
                    .centerCrop()
                    .transform(RoundedCorners(24))
                    .into(ivSearchContent)

                root.setOnClickListener { listener.onItemClick(item) }
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    listener.onBookmarkClick(item.reportId, item.isBookmark, item.tag.text)
                    updateBookmarkIcon(item.isBookmark)
                }
            }

            private fun updateBookmarkIcon(isBookmarked: Boolean) {
                binding.ivSearchContentBookmark.setImageResource(
                    if (isBookmarked) R.drawable.ic_search_fill_bookmark
                    else R.drawable.ic_search_blank_bookmark
                )
            }
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