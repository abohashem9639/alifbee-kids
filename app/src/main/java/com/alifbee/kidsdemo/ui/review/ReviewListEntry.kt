package com.alifbee.kidsdemo.ui.review

import com.alifbee.kidsdemo.data.model.ReviewGroup
import com.alifbee.kidsdemo.data.model.ReviewItem

sealed class ReviewListEntry {
    data class Header(val groupId: String, val title: String) : ReviewListEntry()
    data class Item(val item: ReviewItem) : ReviewListEntry()
}

fun List<ReviewGroup>.toListEntries(): List<ReviewListEntry> = flatMap { group ->
    buildList {
        add(ReviewListEntry.Header(group.id, group.title))
        addAll(group.items.map { ReviewListEntry.Item(it) })
    }
}
