package com.alifbee.kidsdemo.ui.more

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.databinding.FragmentMoreAppsBinding

class MoreAppsFragment : Fragment(R.layout.fragment_more_apps) {

    private var _binding: FragmentMoreAppsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoreAppsViewModel by viewModels()
    private val adapter = MoreAppsAdapter { openStore(it) }

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMoreAppsBinding.bind(view)

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        ViewCompat.setOnApplyWindowInsetsListener(binding.moreAppsRoot) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }

        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.appsList.adapter = adapter
        binding.errorText.setOnClickListener { viewModel.loadApps() }

        viewModel.uiState.observe(viewLifecycleOwner) { state -> render(state) }
    }

    private fun render(state: UiState<List<AppItem>>) {
        binding.progress.isVisible = state is UiState.Loading
        binding.errorText.isVisible = state is UiState.Error
        binding.appsList.isVisible = state is UiState.Success
        if (state is UiState.Success) adapter.submitList(state.data)
    }

    private fun openStore(app: AppItem) {
        if (app.storeUrl.isBlank()) return
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(app.storeUrl)))
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        binding.appsList.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
