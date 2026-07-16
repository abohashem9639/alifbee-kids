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
import androidx.viewpager2.widget.ViewPager2
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val categoryAdapter = CategoryAdapter()

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateArrows(position)
        }
    }

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

        binding.categoryPager.adapter = categoryAdapter
        binding.categoryPager.registerOnPageChangeCallback(pageChangeCallback)
        categoryAdapter.submitList(viewModel.categories) {
            updateArrows(binding.categoryPager.currentItem)
        }

        binding.leftArrow.setOnClickListener {
            binding.categoryPager.currentItem = binding.categoryPager.currentItem - 1
        }
        binding.rightArrow.setOnClickListener {
            binding.categoryPager.currentItem = binding.categoryPager.currentItem + 1
        }
        binding.musicButton.setOnClickListener { viewModel.toggleMusic() }
        binding.moreByUsButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_parents_lock)
        }

        viewModel.musicOn.observe(viewLifecycleOwner) { on ->
            binding.musicButton.setImageResource(
                if (on) R.drawable.ic_music_on else R.drawable.ic_music_off
            )
        }
    }

    private fun updateArrows(index: Int) {
        val lastIndex = (binding.categoryPager.adapter?.itemCount ?: 0) - 1
        binding.leftArrow.isInvisible = index <= 0
        binding.rightArrow.isInvisible = index >= lastIndex
    }

    override fun onDestroyView() {
        binding.categoryPager.unregisterOnPageChangeCallback(pageChangeCallback)
        binding.categoryPager.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
