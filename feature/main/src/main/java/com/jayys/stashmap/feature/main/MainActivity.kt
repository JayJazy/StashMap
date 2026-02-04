package com.jayys.stashmap.feature.main

import androidx.compose.runtime.Composable
import com.jayys.stashmap.feature.main.base.BaseActivity
import com.jayys.stashmap.feature.main.screen.MainScreen

class MainActivity : BaseActivity() {
    @Composable
    override fun Screen() {
        MainScreen()
    }
}