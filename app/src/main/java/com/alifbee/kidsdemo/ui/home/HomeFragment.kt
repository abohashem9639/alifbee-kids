package com.alifbee.kidsdemo.ui.home

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isInvisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private var renderedIndex = 0
    private var firstRender = true
    private var animating = false

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        ViewCompat.setOnApplyWindowInsetsListener(binding.header) { v, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.updatePadding(top = topInset)
            insets
        }

        binding.leftArrow.setOnClickListener { if (!animating) viewModel.previous() }
        binding.rightArrow.setOnClickListener { if (!animating) viewModel.next() }
        binding.musicButton.setOnClickListener { viewModel.toggleMusic() }
        binding.moreByUsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_parents_lock)
        }

        observeState()
    }

    private fun observeState() {
        viewModel.currentIndex.observe(viewLifecycleOwner) { index ->
            updateArrows(index)
            val imageRes = viewModel.categories[index].imageRes
            if (firstRender) {
                binding.categoryImage.setImageResource(imageRes)
                firstRender = false
            } else {
                slide(goingNext = index > renderedIndex, imageRes = imageRes)
            }
            renderedIndex = index
        }
        viewModel.musicOn.observe(viewLifecycleOwner) { on ->
            binding.musicButton.setImageResource(
                if (on) R.drawable.ic_music_on else R.drawable.ic_music_off
            )
        }
    }

    private fun updateArrows(index: Int) {
        binding.leftArrow.isInvisible = index == 0
        binding.rightArrow.isInvisible = index == viewModel.categories.lastIndex
    }

    private fun slide(goingNext: Boolean, imageRes: Int) {
        val width = binding.root.width.toFloat()
        animating = true
        binding.categoryImage.animate()
            .translationX(if (goingNext) -width else width)
            .setDuration(SLIDE_DURATION)
            .withEndAction {
                binding.categoryImage.setImageResource(imageRes)
                binding.categoryImage.translationX = if (goingNext) width else -width
                binding.categoryImage.animate()
                    .translationX(0f)
                    .setDuration(SLIDE_DURATION)
                    .withEndAction { animating = false }
                    .start()
            }
            .start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val SLIDE_DURATION = 100L
    }
}
