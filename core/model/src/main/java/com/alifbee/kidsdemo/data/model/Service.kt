package com.alifbee.kidsdemo.data.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class Service(
    val id: String,
    val name: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val backgroundColorRes: Int,
)
