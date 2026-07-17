package com.alifbee.kidsdemo.ui.splash

import com.alifbee.kidsdemo.ui.common.ObservableView

interface SplashView : ObservableView<SplashView.Listener> {
    fun startTimer()
    fun destroyView()

    interface Listener {
        fun onSplashFinished()
    }
}
