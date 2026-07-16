package com.alifbee.kidsdemo.ui.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.alifbee.kidsdemo.core.UiState
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.databinding.FragmentMoreAppsBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView

class MoreAppsViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<MoreAppsView.Listener>(), MoreAppsView {

    private var _binding: FragmentMoreAppsBinding? = null
    private val binding get() = _binding!!

    private val adapter = MoreAppsAdapter {
        for (listener in listeners){
            listener.onAppClick(it)
        } }

    init {
        _binding = FragmentMoreAppsBinding.inflate(inflater, parent, false)
        binding.appsList.adapter = adapter
        rootView = binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.moreAppsRoot) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }
    }

    override fun render(state: UiState<List<AppItem>>) {
        binding.progress.isVisible = state is UiState.Loading
        binding.errorText.isVisible = state is UiState.Error
        binding.appsList.isVisible = state is UiState.Success
        if (state is UiState.Success) adapter.submitList(state.data)
    }

    override fun onBackClick(action: () -> Unit) {
        binding.backButton.setOnClickListener { action() }
    }

    override fun onRetryClick(action: () -> Unit) {
        binding.errorText.setOnClickListener { action() }
    }


    override fun destroyView() {
        _binding?.appsList?.adapter = null
        _binding = null
    }
}
