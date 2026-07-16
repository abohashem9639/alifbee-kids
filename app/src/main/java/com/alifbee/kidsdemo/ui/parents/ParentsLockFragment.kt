package com.alifbee.kidsdemo.ui.parents

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Space
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.databinding.FragmentParentsLockBinding
import com.alifbee.kidsdemo.ui.components.PushButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParentsLockFragment : Fragment(R.layout.fragment_parents_lock) {

    private var _binding: FragmentParentsLockBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ParentsLockViewModel by viewModels()

    private lateinit var pinCells: List<TextView>

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentParentsLockBinding.bind(view)

        val window = requireActivity().window
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        ViewCompat.setOnApplyWindowInsetsListener(binding.parentsLockRoot) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }

        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.codeWords.text = viewModel.codeWords

        pinCells = listOf(binding.pin0, binding.pin1, binding.pin2)

        buildKeypad()
        observeState()
    }

    private fun buildKeypad() {
        val inflater = LayoutInflater.from(requireContext())
        for (label in KEY_LABELS) {
            when (label) {
                KEY_EMPTY -> binding.keypad.addView(Space(requireContext()), cellParams())
                KEY_BACKSPACE -> {
                    val key = inflater.inflate(R.layout.item_key, binding.keypad, false) as PushButton
                    key.setText(label)
                    key.setFaceColor(ContextCompat.getColor(requireContext(), R.color.key_backspace))
                    key.setLabelColor(ContextCompat.getColor(requireContext(), R.color.white))
                    key.setOnClickListener { viewModel.onDelete() }
                    binding.keypad.addView(key)
                }
                else -> {
                    val key = inflater.inflate(R.layout.item_key, binding.keypad, false) as PushButton
                    key.setText(label)
                    key.setOnClickListener { viewModel.onDigit(label.toInt()) }
                    binding.keypad.addView(key)
                }
            }
        }
    }

    private fun cellParams(): GridLayout.LayoutParams {
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        return GridLayout.LayoutParams().apply {
            width = 0
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            setMargins(margin, margin, margin, margin)
        }
    }

    private fun observeState() {
        viewModel.entered.observe(viewLifecycleOwner) { entered ->
            pinCells.forEachIndexed { i, cell ->
                cell.text = entered.getOrNull(i)?.toString().orEmpty()
            }
        }
        viewModel.unlocked.observe(viewLifecycleOwner) { unlocked ->
            if (unlocked) {
                viewModel.onUnlockHandled()
                findNavController().navigate(R.id.action_parents_lock_to_more_apps)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val KEY_EMPTY = ""
        private const val KEY_BACKSPACE = "<<"
        private val KEY_LABELS = listOf(
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            KEY_EMPTY, "0", KEY_BACKSPACE
        )
    }
}
