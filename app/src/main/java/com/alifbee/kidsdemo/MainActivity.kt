package com.alifbee.kidsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.alifbee.kidsdemo.data.datastore.DataStoreManager
import com.alifbee.kidsdemo.data.model.ThemeMode
import com.alifbee.kidsdemo.ui.AlifBeeApp
import com.alifbee.kidsdemo.ui.ProvideAppLocale
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val initialLanguage = runBlocking { dataStoreManager.appLanguage.first() }
        val initialThemeMode = runBlocking { dataStoreManager.appThemeMode.first() }
        setContent {
            val language by dataStoreManager.appLanguage.collectAsState(initial = initialLanguage)
            val themeMode by dataStoreManager.appThemeMode.collectAsState(initial = initialThemeMode)
            val isDark = themeMode == ThemeMode.DARK
            ProvideAppLocale(languageCode = language, darkTheme = isDark) {
                KidsDemoTheme(darkTheme = isDark) {
                    AlifBeeApp()
                }
            }
        }
    }
}
