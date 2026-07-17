package com.alifbee.kidsdemo.ui.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.data.model.AppItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreAppsFragment : Fragment(), MoreAppsView.Listener {

    private lateinit var mView: MoreAppsView

    private val viewModel: MoreAppsViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = MoreAppsViewImpl(inflater, container)
        mView = view
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        mView.registerListener(this)
        mView.onBackClick { findNavController().navigateUp() }
        mView.onRetryClick { viewModel.loadApps() }

        viewModel.uiState.observe(viewLifecycleOwner) { state -> mView.render(state) }
    }

    override fun onAppClick(action: (AppItem)) {
        if (action.storeUrl.isBlank()) return
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(action.storeUrl)))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        mView.unregisterListener(this)
        mView.destroyView()
        super.onDestroyView()
    }
}
