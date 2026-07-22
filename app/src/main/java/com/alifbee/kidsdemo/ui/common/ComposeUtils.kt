package com.alifbee.kidsdemo.ui.common

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> Flow<T>.CollectEffect(onEffect: suspend (effect: T) -> Unit) {
    LaunchedEffect(this) {
        this@CollectEffect.onEach { onEffect(it) }.collect {}
    }
}

class ClickDelay(private val delayMillis: Long = 600L) {

    private var lastClickTime = 0L

    fun clickDelay(action: () -> Unit) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastClickTime >= delayMillis) {
            lastClickTime = now
            action()
        }
    }
}

@Composable
fun rememberClickDelay(): ClickDelay = remember { ClickDelay() }
