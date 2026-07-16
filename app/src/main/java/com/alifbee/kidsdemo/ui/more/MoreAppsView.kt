package com.alifbee.kidsdemo.ui.more

import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.ui.common.ObservableView

interface MoreAppsView : ObservableView<MoreAppsView.Listener> {
    fun render(state: UiState<List<AppItem>>)
    fun onBackClick(action: () -> Unit)
    fun onRetryClick(action: () -> Unit)
    fun destroyView()


    interface Listener {
        fun onAppClick(action: (AppItem))
    }
}
