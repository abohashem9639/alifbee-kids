package com.alifbee.kidsdemo.data.model

enum class ThemeMode {
    LIGHT,
    DARK;

    companion object {
        fun fromKey(key: String?): ThemeMode = entries.firstOrNull { it.name == key } ?: LIGHT
    }
}
