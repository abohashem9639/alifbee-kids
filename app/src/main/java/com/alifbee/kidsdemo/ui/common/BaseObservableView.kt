package com.alifbee.kidsdemo.ui.common

import java.util.Collections

abstract class BaseObservableView<ListenerType> : BaseViewImp(),
    ObservableView<ListenerType> {


    private val mListeners: MutableSet<ListenerType> = HashSet()
    override fun registerListener(listener: ListenerType) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        mListeners.remove(listener)
    }

    protected val listeners: Set<ListenerType>
        get() = Collections.unmodifiableSet(mListeners)
}