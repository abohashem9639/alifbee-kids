package com.alifbee.kidsdemo.data.model

import androidx.annotation.DrawableRes

data class AppLanguage(
    val code: String,
    val name: String,
    @DrawableRes val flagRes: Int,
)
