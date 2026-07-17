package com.alifbee.kidsdemo.ui.home

import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.ui.common.ObservableView

interface HomeView : ObservableView<HomeView.Listener> {
    fun bindCategories(categories: List<Category>)
    fun renderMusicState(on: Boolean)
    fun onMusicClick(action: () -> Unit)
    fun onMoreByUsClick(action: () -> Unit)
    fun destroyView()

    interface Listener
}
