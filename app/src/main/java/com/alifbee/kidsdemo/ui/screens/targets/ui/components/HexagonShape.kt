package com.alifbee.kidsdemo.ui.screens.targets.ui.components

import androidx.compose.foundation.shape.GenericShape

val HexagonShape = GenericShape { size, _ ->
    val width = size.width
    val hexHeight = width * 0.866f
    val top = (size.height - hexHeight) / 2f
    val bottom = top + hexHeight
    val midY = size.height / 2f
    moveTo(width * 0.25f, top)
    lineTo(width * 0.75f, top)
    lineTo(width, midY)
    lineTo(width * 0.75f, bottom)
    lineTo(width * 0.25f, bottom)
    lineTo(0f, midY)
    close()
}
