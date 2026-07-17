package com.alifbee.kidsdemo.ui.parents

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentsLockFragment : Fragment(), ParentsLockView.Listener {

    private var mView: ParentsLockView? = null

    private val viewModel: ParentsLockViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = ParentsLockViewImpl(inflater, container)
        mView = view
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = mView ?: return

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        screen.registerListener(this)
        screen.bindCodeWords(viewModel.codeWords)
        screen.onBackClick { findNavController().navigateUp() }

        viewModel.entered.observe(viewLifecycleOwner) { entered -> screen.renderEntered(entered) }
        viewModel.unlocked.observe(viewLifecycleOwner) { unlocked ->
            if (unlocked) {
                viewModel.onUnlockHandled()
                findNavController().navigate(R.id.action_parents_lock_to_more_apps)
            }
        }
    }

    override fun onDigit(digit: Int) {
        viewModel.onDigit(digit)
    }

    override fun onDelete() {
        viewModel.onDelete()
    }

    override fun onDestroyView() {
        mView?.unregisterListener(this)
        mView?.destroyView()
        mView = null
        super.onDestroyView()
    }
}
