package com.alifbee.kidsdemo.ui.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.alifbee.kidsdemo.databinding.FragmentReviewBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView
import com.alifbee.kidsdemo.ui.components.ReviewGroupsAdapter
import com.google.android.material.appbar.AppBarLayout

class ReviewViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<ReviewView.Listener>(), ReviewView {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val listAdapter = ReviewGroupsAdapter(
        onItemClick = { item ->
            for (listener in listeners) {
                listener.onReviewItemClick(item)
            }
        },
        onAudioClick = { item ->
            for (listener in listeners) {
                listener.onReviewItemAudioClick(item)
            }
        },
        onBookmarkClick = { item ->
            for (listener in listeners) {
                listener.onReviewItemBookmarkClick(item)
            }
        },
    )

    private val offsetListener = AppBarLayout.OnOffsetChangedListener { appBar, verticalOffset ->
        val range = appBar.totalScrollRange.toFloat()
        val progress = if (range == 0f) 0f else (-verticalOffset / range).coerceIn(0f, 1f)
        _binding?.reviewHeader?.progress = progress
    }

    init {
        _binding = FragmentReviewBinding.inflate(inflater, parent, false)
        rootView = binding.root
        binding.reviewAppBar.addOnOffsetChangedListener(offsetListener)
        binding.reviewList.apply {
            layoutManager = LinearLayoutManager(rootContext)
            adapter = listAdapter
        }
    }

    override fun bindItemsReadyCount(count: Int) {
        binding.reviewCount.text = count.toString()
        binding.reviewHeader.rebuildScene()
        binding.reviewCount.doOnLayout { view ->
            view.pivotX = 0f
            view.pivotY = view.height.toFloat()
        }
        binding.reviewSubtitle.doOnLayout { view ->
            view.pivotX = 0f
            view.pivotY = view.height.toFloat()
        }
    }

    override fun renderList(entries: List<ReviewListEntry>) {
        listAdapter.submitList(entries)
    }

    override fun onReviewAllClick(action: () -> Unit) {
        binding.reviewAllButton.setOnClickListener { action() }
    }

    override fun onInfoClick(action: () -> Unit) {
        binding.reviewInfo.setOnClickListener { action() }
    }

    override fun destroyView() {
        _binding?.reviewAppBar?.removeOnOffsetChangedListener(offsetListener)
        _binding?.reviewList?.adapter = null
        _binding = null
    }
}
