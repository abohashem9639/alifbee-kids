package com.alifbee.kidsdemo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isInvisible
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.databinding.FragmentHomeBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView
import com.alifbee.kidsdemo.ui.components.CategoryAdapter

class HomeViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<HomeView.Listener>(), HomeView {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val categoryAdapter = CategoryAdapter()

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateArrows(position)
        }
    }

    init {
        _binding = FragmentHomeBinding.inflate(inflater, parent, false)
        rootView = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(binding.header) { v, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.updatePadding(top = topInset)
            insets
        }

        binding.categoryPager.adapter = categoryAdapter
        binding.categoryPager.registerOnPageChangeCallback(pageChangeCallback)

        binding.leftArrow.setOnClickListener {
            binding.categoryPager.currentItem = binding.categoryPager.currentItem - 1
        }
        binding.rightArrow.setOnClickListener {
            binding.categoryPager.currentItem = binding.categoryPager.currentItem + 1
        }
    }

    override fun bindCategories(categories: List<Category>) {
        categoryAdapter.submitList(categories) {
            updateArrows(binding.categoryPager.currentItem)
        }
    }

    override fun renderMusicState(on: Boolean) {
        binding.musicButton.setImageResource(
            if (on) R.drawable.ic_music_on else R.drawable.ic_music_off
        )
    }

    override fun onMusicClick(action: () -> Unit) {
        binding.musicButton.setOnClickListener { action() }
    }

    override fun onMoreByUsClick(action: () -> Unit) {
        binding.moreByUsButton.setOnClickListener { action() }
    }

    private fun updateArrows(index: Int) {
        val lastIndex = (binding.categoryPager.adapter?.itemCount ?: 0) - 1
        binding.leftArrow.isInvisible = index <= 0
        binding.rightArrow.isInvisible = index >= lastIndex
    }

    override fun destroyView() {
        _binding?.categoryPager?.unregisterOnPageChangeCallback(pageChangeCallback)
        _binding?.categoryPager?.adapter = null
        _binding = null
    }
}
