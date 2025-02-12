package com.example.findu.presentation.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.databinding.SearchHorizontalContentItemBinding
import com.example.findu.databinding.ItemSearchGridContentBinding
import com.example.findu.domain.model.search.SearchData

class SearchContentRVAdapter(
    private val items: ArrayList<SearchData>,
    private val onItemClick: (SearchData) -> Unit
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

    inner class HorizontalViewHolder(private val binding: SearchHorizontalContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchData) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                ivSearchContent.setImageResource(item.image)

                updateBookmark(item.isBookmark)
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    updateBookmark(item.isBookmark)
                }
                tvSearchContentStatus.text = item.status.text
                tvSearchContentStatus.setTextColor(itemView.context.getColor(item.status.textColor))
                tvSearchContentStatus.setBackgroundResource(item.status.backgroundRes)

                root.setOnClickListener {
                    onItemClick(item)
                }
            }

        }

        private fun updateBookmark(isBookmark: Boolean) {
            with(binding) {
                ivSearchContentBookmark.setImageResource(
                    if (isBookmark) R.drawable.ic_search_fill_bookmark
                    else R.drawable.ic_search_blank_bookmark
                )
            }
        }
    }

    inner class GridViewHolder(private val binding: ItemSearchGridContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchData) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                ivSearchContent.setImageResource(item.image)

                updateBookmark(item.isBookmark)
                ivSearchContentBookmark.setOnClickListener {
                    item.isBookmark = !item.isBookmark
                    updateBookmark(item.isBookmark)
                }

                tvSearchContentStatus.text = item.status.text
                tvSearchContentStatus.setTextColor(itemView.context.getColor(item.status.textColor))
                tvSearchContentStatus.setBackgroundResource(item.status.backgroundRes)
                root.setOnClickListener {
                    onItemClick(item)
                }
            }

        }

        private fun updateBookmark(isBookmark: Boolean) {
            with(binding) {
                ivSearchContentBookmark.setImageResource(
                    if (isBookmark) R.drawable.ic_search_fill_bookmark
                    else R.drawable.ic_search_blank_bookmark_grid
                )
            }
        }


    }
}