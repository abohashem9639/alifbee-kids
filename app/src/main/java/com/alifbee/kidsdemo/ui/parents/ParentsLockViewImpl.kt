package com.alifbee.kidsdemo.ui.parents

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.alifbee.kidsdemo.databinding.FragmentParentsLockBinding
import com.alifbee.kidsdemo.ui.common.BaseObservableView

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

        setupKeypad()
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

    private fun setupKeypad() {
        binding.key1.setOnClickListener { notifyDigit(1) }
        binding.key2.setOnClickListener { notifyDigit(2) }
        binding.key3.setOnClickListener { notifyDigit(3) }
        binding.key4.setOnClickListener { notifyDigit(4) }
        binding.key5.setOnClickListener { notifyDigit(5) }
        binding.key6.setOnClickListener { notifyDigit(6) }
        binding.key7.setOnClickListener { notifyDigit(7) }
        binding.key8.setOnClickListener { notifyDigit(8) }
        binding.key9.setOnClickListener { notifyDigit(9) }
        binding.key0.setOnClickListener { notifyDigit(0) }
        binding.keyBackspace.setOnClickListener {
            for (listener in listeners) {
                listener.onDelete()
            }
        }
    }

    private fun notifyDigit(digit: Int) {
        for (listener in listeners) {
            listener.onDigit(digit)
        }
    }

    override fun destroyView() {
        _binding = null
    }
}
