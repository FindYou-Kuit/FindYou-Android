package com.example.findu.presentation.ui.report.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.findu.R
import com.example.findu.databinding.ItemReportDefaultImageBinding
import com.example.findu.databinding.ItemReportUploadedImageBinding
import com.example.findu.presentation.type.report.ReportType

class ReportImageAdapter(
    val context: Context,
    val reportType: ReportType,
    private val onRemoveClickListener: (Int) -> Unit,
    private val onUploadClickListener: () -> Unit
) : ListAdapter<Uri, RecyclerView.ViewHolder>(diffUtil) {

    inner class DefaultImageViewHolder(private val binding: ItemReportDefaultImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvReportDefaultCount.text = context.getString(
                R.string.report_image_count, currentList.size - 1
            )
            Log.d("ReportImageAdapteras", currentList.size.toString())

            binding.root.setOnClickListener {
                onUploadClickListener()
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
                onRemoveClickListener(layoutPosition)
            }

            Glide.with(binding.root)
                .load(uri)
                .into(binding.ivReportUploadedImage)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DefaultImageViewHolder -> holder.bind()
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