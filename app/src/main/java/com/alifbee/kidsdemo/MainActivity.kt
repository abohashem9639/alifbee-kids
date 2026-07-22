package com.alifbee.kidsdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alifbee.kidsdemo.ui.AlifBeeApp
import com.alifbee.kidsdemo.ui.theme.KidsDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KidsDemoTheme {
                AlifBeeApp()
            }
        }
    }
}
