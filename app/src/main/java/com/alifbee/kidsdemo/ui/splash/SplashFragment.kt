package com.alifbee.kidsdemo.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R

class SplashFragment : Fragment(), SplashView.Listener {

    private var mView: SplashView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = SplashViewImpl(inflater, container)
        mView = view
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = mView ?: return
        screen.registerListener(this)
        screen.startTimer()
    }

    override fun onSplashFinished() {
        findNavController().navigate(R.id.action_splash_to_home)
    }

    override fun onDestroyView() {
        mView?.unregisterListener(this)
        mView?.destroyView()
        mView = null
        super.onDestroyView()
    }
}
