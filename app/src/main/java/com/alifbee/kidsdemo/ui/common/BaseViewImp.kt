package com.alifbee.kidsdemo.ui.common

import android.content.Context
import android.view.View

abstract class BaseViewImp : BaseView {
    private lateinit var _rootView: View

    override var rootView
        get() = _rootView
        protected set(value) {
            _rootView = value
        }

    protected val rootContext: Context
        get() = rootView.context

    protected open fun <T : View?> findViewById(id: Int): T {
        return rootView.findViewById(id)
    }
}