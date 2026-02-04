package com.jayys.stashmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jayys.stashmap.core.designsystem.theme.StashMapTheme
import com.jayys.stashmap.feature.main.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = resolveRoute(intent.data)

        setContent {
            StashMapTheme {
                MainScreen(startDestination = startDestination)
            }
        }
    }
}