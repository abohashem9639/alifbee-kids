package com.alifbee.kidsdemo.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.postDelayed({
            findNavController().navigate(R.id.action_splash_to_home)
        }, SPLASH_DELAY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        private const val SPLASH_DELAY = 3000L
    }
}
