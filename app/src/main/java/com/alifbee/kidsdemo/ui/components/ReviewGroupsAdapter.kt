package com.alifbee.kidsdemo.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.databinding.ItemReviewEntryBinding
import com.alifbee.kidsdemo.databinding.ItemReviewGroupHeaderBinding
import com.alifbee.kidsdemo.ui.review.ReviewListEntry

class ReviewGroupsAdapter(
    private val onItemClick: (ReviewItem) -> Unit,
    private val onAudioClick: (ReviewItem) -> Unit,
    private val onBookmarkClick: (ReviewItem) -> Unit,
) : ListAdapter<ReviewListEntry, RecyclerView.ViewHolder>(DIFF) {

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is ReviewListEntry.Header -> VIEW_TYPE_HEADER
        is ReviewListEntry.Item -> VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemReviewGroupHeaderBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_ITEM -> ItemViewHolder(
                ItemReviewEntryBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val entry = getItem(position)) {
            is ReviewListEntry.Header -> (holder as HeaderViewHolder).bind(entry)
            is ReviewListEntry.Item -> (holder as ItemViewHolder).bind(entry.item)
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemReviewGroupHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: ReviewListEntry.Header) {
            binding.groupTitle.text = entry.title
        }
    }

    inner class ItemViewHolder(
        private val binding: ItemReviewEntryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReviewItem) {
            binding.itemArabic.text = item.arabic
            binding.itemEnglish.text = item.english.orEmpty()
            binding.itemEnglish.isVisible = !item.english.isNullOrBlank()

            if (item.iconRes != null) {
                binding.itemIconWrapper.visibility = android.view.View.VISIBLE
                binding.itemIcon.setImageResource(item.iconRes)
            } else {
                binding.itemIconWrapper.visibility = android.view.View.INVISIBLE
            }

            binding.itemBookmark.setImageResource(
                if (item.isBookmarked) R.drawable.ic_bookmark_filled
                else R.drawable.ic_bookmark_outline
            )

            binding.root.setOnClickListener { onItemClick(item) }
            binding.itemAudio.setOnClickListener { onAudioClick(item) }
            binding.itemBookmark.setOnClickListener { onBookmarkClick(item) }
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1

        private val DIFF = object : DiffUtil.ItemCallback<ReviewListEntry>() {
            override fun areItemsTheSame(oldItem: ReviewListEntry, newItem: ReviewListEntry): Boolean = when {
                oldItem is ReviewListEntry.Header && newItem is ReviewListEntry.Header ->
                    oldItem.groupId == newItem.groupId
                oldItem is ReviewListEntry.Item && newItem is ReviewListEntry.Item ->
                    oldItem.item.id == newItem.item.id
                else -> false
            }

            override fun areContentsTheSame(oldItem: ReviewListEntry, newItem: ReviewListEntry): Boolean =
                oldItem == newItem
        }
    }
}
