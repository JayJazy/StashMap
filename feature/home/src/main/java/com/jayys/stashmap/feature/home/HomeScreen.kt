package com.jayys.stashmap.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.jayys.stashmap.core.designsystem.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = colorResource(id = R.color.bg_color))
            .fillMaxSize()
    ) {
        Text(text = "Home")
    }
}