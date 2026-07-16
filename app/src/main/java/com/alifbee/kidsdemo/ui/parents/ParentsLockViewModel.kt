package com.alifbee.kidsdemo.ui.parents

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alifbee.kidsdemo.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ParentsLockViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val code = List(CODE_LENGTH) { Random.nextInt(1, 10) }

    val codeWords: String = code.joinToString(", ") {
        context.resources.getStringArray(R.array.number_words)[it]
    }

    private val _entered = MutableLiveData<List<Int>>(emptyList())
    val entered: LiveData<List<Int>> = _entered

    private val _unlocked = MutableLiveData(false)
    val unlocked: LiveData<Boolean> = _unlocked

    fun onDigit(digit: Int) {
        val current = _entered.value ?: emptyList()
        if (current.size >= code.size) return
        val updated = current + digit
        if (updated.size < code.size) {
            _entered.value = updated
        } else {
            if (updated == code) _unlocked.value = true
            _entered.value = emptyList()
        }
    }

    fun onUnlockHandled() {
        _unlocked.value = false
    }

    fun onDelete() {
        val current = _entered.value ?: emptyList()
        if (current.isNotEmpty()) _entered.value = current.dropLast(1)
    }

    companion object {
        private const val CODE_LENGTH = 3
    }
}
