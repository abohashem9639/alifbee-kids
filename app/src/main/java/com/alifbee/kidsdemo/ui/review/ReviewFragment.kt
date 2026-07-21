package com.alifbee.kidsdemo.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.ReviewItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : Fragment(), ReviewView.Listener {

    private var mView: ReviewView? = null

    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ReviewViewImpl(inflater, container)
        mView = view
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = mView ?: return

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        screen.registerListener(this)
        screen.onReviewAllClick { }
        screen.onInfoClick { }

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            if (state is UiState.Success) {
                screen.bindItemsReadyCount(state.data.itemsReadyCount)
                screen.renderList(state.data.groups.toListEntries())
            }
        }
    }

    override fun onReviewItemClick(item: ReviewItem) {}

    override fun onReviewItemAudioClick(item: ReviewItem) {}

    override fun onReviewItemBookmarkClick(item: ReviewItem) {}

    override fun onDestroyView() {
        mView?.unregisterListener(this)
        mView?.destroyView()
        mView = null
        super.onDestroyView()
    }
}
