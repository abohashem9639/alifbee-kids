package com.alifbee.kidsdemo.ui.splash

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.alifbee.kidsdemo.databinding.FragmentSplashBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView

class SplashViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<SplashView.Listener>(), SplashView {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())

    init {
        _binding = FragmentSplashBinding.inflate(inflater, parent, false)
        rootView = binding.root
    }

    override fun startTimer() {
        handler.postDelayed({
            for (listener in listeners) {
                listener.onSplashFinished()
            }
        }, SPLASH_DELAY)
    }

    override fun destroyView() {
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }

    companion object {
        private const val SPLASH_DELAY = 3000L
    }
}
