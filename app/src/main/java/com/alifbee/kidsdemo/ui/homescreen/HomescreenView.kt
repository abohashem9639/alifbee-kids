package com.alifbee.kidsdemo.ui.homescreen

import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.HomescreenData
import com.alifbee.kidsdemo.ui.common.ObservableView

interface HomescreenView : ObservableView<HomescreenView.Listener> {
    fun render(state: UiState<HomescreenData>)
    fun onSettingsClick(action: () -> Unit)
    fun onProfileClick(action: () -> Unit)
    fun onGoPremiumClick(action: () -> Unit)
    fun onCurrentCourseClick(action: () -> Unit)
    fun onSeeAllCoursesClick(action: () -> Unit)
    fun destroyView()

    interface Listener
}
