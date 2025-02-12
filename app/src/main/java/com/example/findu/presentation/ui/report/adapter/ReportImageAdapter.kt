package com.example.findu.presentation.ui.report.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.databinding.ItemReportDefaultImageBinding
import com.example.findu.databinding.ItemReportUploadedImageBinding
import com.example.findu.presentation.type.report.ReportType

class ReportImageAdapter(
    val reportType: ReportType,
    val onAIButtonClick : (Uri) -> Unit
) : ListAdapter<Uri, RecyclerView.ViewHolder>(diffUtil) {

    inner class DefaultImageViewHolder(private val binding: ItemReportDefaultImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(count: Int) {
            // 사진 개수 카운트
            binding.tvReportDefaultCount.text = (count - 1).toString()

            binding.root.setOnClickListener {
                // TODO : 사진 이미지 업로드 기능 설정
            }
        }
    }

    inner class ImageViewHolder(private val binding: ItemReportUploadedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            when (reportType) {
                // 목격 신고
                ReportType.WITNESS -> {
                    binding.btnReportUploadedAiDistinction.setOnClickListener {
                        // TODO : AI 버튼 클릭 이벤트 설정
                    }
                }
                // 실종 신고
                ReportType.MISSING -> {
                    binding.btnReportUploadedAiDistinction.visibility = View.GONE
                }
            }

            binding.ivReportUploadedImageClose.setOnClickListener {
                removeItem(uri)
            }

            Glide.with(binding.root)
                .load(uri)
                .into(binding.ivReportUploadedImage)

            binding.btnReportUploadedAiDistinction.setOnClickListener {
                onAIButtonClick(uri)
            }

        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DefaultImageViewHolder -> holder.bind(currentList.size)
            is ImageViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            // 0 번째 아이템
            DEFAULT_VIEW_TYPE -> {
                DefaultImageViewHolder(
                    ItemReportDefaultImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            // IMAGE_VIEW_TYPE, 정상적으로 업로드될 이미지
            else -> {
                ImageViewHolder(
                    ItemReportUploadedImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

        }

    fun removeItem(uri: Uri) {
        val list = currentList.toMutableList()
        list.remove(uri)
        submitList(list)
//        notifyItemChanged(0)
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) DEFAULT_VIEW_TYPE // 0번째 아이템은 DefaultImageViewHolder
        else IMAGE_VIEW_TYPE

    companion object {

        private const val DEFAULT_VIEW_TYPE = 0
        private const val IMAGE_VIEW_TYPE = 1

        val diffUtil = object : DiffUtil.ItemCallback<Uri>() {

            override fun areItemsTheSame(
                oldItem: Uri,
                newItem: Uri
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Uri,
                newItem: Uri
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}