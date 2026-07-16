package com.alifbee.kidsdemo.ui.common

interface ObservableView<ListenerType> : BaseView {
    fun registerListener(listener: ListenerType)
    fun unregisterListener(listener: ListenerType)
}