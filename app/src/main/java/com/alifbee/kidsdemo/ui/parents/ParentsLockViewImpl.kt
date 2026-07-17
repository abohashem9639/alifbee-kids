package com.alifbee.kidsdemo.ui.parents

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Space
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.databinding.FragmentParentsLockBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView
import com.alifbee.kidsdemo.ui.components.PushButton

class ParentsLockViewImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableView<ParentsLockView.Listener>(), ParentsLockView {

    private var _binding: FragmentParentsLockBinding? = null
    private val binding get() = _binding!!

    private val pinCells: List<TextView>

    init {
        _binding = FragmentParentsLockBinding.inflate(inflater, parent, false)
        rootView = binding.root

        ViewCompat.setOnApplyWindowInsetsListener(binding.parentsLockRoot) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }

        pinCells = listOf(binding.pin0, binding.pin1, binding.pin2)

        buildKeypad(inflater)
    }

    override fun bindCodeWords(text: String) {
        binding.codeWords.text = text
    }

    override fun renderEntered(entered: List<Int>) {
        pinCells.forEachIndexed { i, cell ->
            cell.text = entered.getOrNull(i)?.toString().orEmpty()
        }
    }

    override fun onBackClick(action: () -> Unit) {
        binding.backButton.setOnClickListener { action() }
    }

    private fun buildKeypad(inflater: LayoutInflater) {
        val context = rootContext
        for (label in KEY_LABELS) {
            when (label) {
                KEY_EMPTY -> binding.keypad.addView(Space(context), cellParams())
                KEY_BACKSPACE -> {
                    val key = inflater.inflate(R.layout.item_key, binding.keypad, false) as PushButton
                    key.setText(label)
                    key.setFaceColor(ContextCompat.getColor(context, R.color.key_backspace))
                    key.setLabelColor(ContextCompat.getColor(context, R.color.white))
                    key.setOnClickListener {
                        for (listener in listeners) {
                            listener.onDelete()
                        }
                    }
                    binding.keypad.addView(key)
                }
                else -> {
                    val key = inflater.inflate(R.layout.item_key, binding.keypad, false) as PushButton
                    key.setText(label)
                    key.setOnClickListener {
                        val digit = label.toInt()
                        for (listener in listeners) {
                            listener.onDigit(digit)
                        }
                    }
                    binding.keypad.addView(key)
                }
            }
        }
    }

    private fun cellParams(): GridLayout.LayoutParams {
        val margin = rootContext.resources.getDimensionPixelSize(R.dimen.spacing_xs)
        return GridLayout.LayoutParams().apply {
            width = 0
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            setMargins(margin, margin, margin, margin)
        }
    }

    override fun destroyView() {
        _binding = null
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
