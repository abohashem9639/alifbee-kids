package com.alifbee.kidsdemo.ui.review

import com.alifbee.kidsdemo.data.model.ReviewItem
import com.alifbee.kidsdemo.ui.common.ObservableView

interface ReviewView : ObservableView<ReviewView.Listener> {
    fun bindItemsReadyCount(count: Int)
    fun renderList(entries: List<ReviewListEntry>)
    fun onReviewAllClick(action: () -> Unit)
    fun onInfoClick(action: () -> Unit)
    fun destroyView()

    interface Listener {
        fun onReviewItemClick(item: ReviewItem)
        fun onReviewItemAudioClick(item: ReviewItem)
        fun onReviewItemBookmarkClick(item: ReviewItem)
    }
}
