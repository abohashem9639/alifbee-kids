package com.alifbee.kidsdemo.data.model

import androidx.annotation.DrawableRes

data class Feature(
    val id: String,
    val name: String,
    @DrawableRes val iconRes: Int,
)
