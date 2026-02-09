package com.jayys.stashmap.feature.main

import androidx.compose.runtime.Composable
import com.jayys.stashmap.base.BaseActivity
import com.jayys.stashmap.feature.main.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Composable
    override fun Screen() {
        MainScreen()
    }
}