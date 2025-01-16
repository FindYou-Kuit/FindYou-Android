package com.example.findu.presentation.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.findu.R
import com.example.findu.databinding.SearchHorizontalContentItemBinding
import com.example.findu.databinding.SearchGridContentItemBinding // 그리드 아이템의 바인딩 추가
import com.example.findu.presentation.ui.search.model.SearchData

class SearchContentRVAdapter(
    private val items: ArrayList<SearchData>,
    private val onItemClick: (SearchData) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HORIZONTAL = 0
        const val VIEW_TYPE_GRID = 1
    }

    private var isGridMode = false

    @SuppressLint("NotifyDataSetChanged")
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
                val binding = SearchGridContentItemBinding.inflate(
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
        val bookmark: ImageView = itemView.findViewById(R.id.iv_search_content_bookmark)
        fun bind(item: SearchData) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                ivSearchContent.setImageResource(item.image)
                root.setOnClickListener() {
                    onItemClick(item)
                }
            }
            updateBookmark(item.isBookmark)
            bookmark.setOnClickListener {
                item.isBookmark = !item.isBookmark
                updateBookmark(item.isBookmark)
            }
        }

        private fun updateBookmark(isBookmark: Boolean) {
            if (isBookmark) {
                bookmark.setImageResource(R.drawable.ic_search_content_fill_bookmark)
            } else {
                bookmark.setImageResource(R.drawable.ic_search_content_blank_bookmark)
            }
        }
    }

    inner class GridViewHolder(private val binding: SearchGridContentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bookmark: ImageView = itemView.findViewById(R.id.iv_search_content_bookmark)
        fun bind(item: SearchData) {
            with(binding) {
                tvSearchContentName.text = item.name
                tvSearchContentDate.text = item.date
                tvSearchContentAddress.text = item.address
                ivSearchContent.setImageResource(item.image)
                root.setOnClickListener() {
                    onItemClick(item)
                }
            }
            updateBookmark(item.isBookmark)
            bookmark.setOnClickListener {
                item.isBookmark = !item.isBookmark
                updateBookmark(item.isBookmark)
            }
        }

        private fun updateBookmark(isBookmark: Boolean) {
            if (isBookmark) {
                bookmark.setImageResource(R.drawable.ic_search_content_fill_bookmark)
            } else {
                bookmark.setImageResource(R.drawable.ic_search_content_blank_bookmark)
            }
        }

    }
}