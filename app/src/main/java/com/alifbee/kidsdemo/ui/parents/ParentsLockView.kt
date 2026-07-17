package com.alifbee.kidsdemo.ui.parents

import com.alifbee.kidsdemo.ui.common.ObservableView

interface ParentsLockView : ObservableView<ParentsLockView.Listener> {
    fun bindCodeWords(text: String)
    fun renderEntered(entered: List<Int>)
    fun onBackClick(action: () -> Unit)
    fun destroyView()

    interface Listener {
        fun onDigit(digit: Int)
        fun onDelete()
    }
}
