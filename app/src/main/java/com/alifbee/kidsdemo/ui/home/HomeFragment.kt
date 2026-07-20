package com.alifbee.kidsdemo.ui.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeView.Listener {

    private var mView: HomeView? = null

    private val viewModel: HomeViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        systemBarsController().apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    override fun onPause() {
        super.onPause()
        systemBarsController().show(WindowInsetsCompat.Type.systemBars())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = HomeViewImpl(inflater, container)
        mView = view
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = mView ?: return

        systemBarsController().isAppearanceLightStatusBars = true

        screen.registerListener(this)
        screen.bindCategories(viewModel.categories)
        screen.onMusicClick {
            findNavController().navigate(R.id.action_home_to_homescreen)
        }
        screen.onMoreByUsClick {
            findNavController().navigate(R.id.action_home_to_parents_lock)
        }

        viewModel.musicOn.observe(viewLifecycleOwner) { on -> screen.renderMusicState(on) }
    }

    override fun onDestroyView() {
        mView?.unregisterListener(this)
        mView?.destroyView()
        mView = null
        super.onDestroyView()
    }

    private fun systemBarsController(): WindowInsetsControllerCompat {
        val window = requireActivity().window
        return WindowInsetsControllerCompat(window, window.decorView)
    }
}
