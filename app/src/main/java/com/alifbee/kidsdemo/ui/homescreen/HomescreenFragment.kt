package com.alifbee.kidsdemo.ui.homescreen

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomescreenFragment : Fragment(), HomescreenView.Listener {

    private var mView: HomescreenView? = null

    private val viewModel: HomescreenViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = HomescreenViewImpl(inflater, container)
        mView = view
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = mView ?: return

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        screen.registerListener(this)
        screen.onSettingsClick { }
        screen.onProfileClick { }
        screen.onGoPremiumClick { }
        screen.onCurrentCourseClick { }
        screen.onSeeAllCoursesClick { }
        screen.onAssignmentClick { }
        screen.onAssignmentStatusClick { }

        viewModel.uiState.observe(viewLifecycleOwner) { state -> screen.render(state) }
    }

    override fun onDestroyView() {
        mView?.unregisterListener(this)
        mView?.destroyView()
        mView = null
        super.onDestroyView()
    }
}
