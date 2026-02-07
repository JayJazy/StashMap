package com.jayys.stashmap.feature.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jayys.stashmap.core.designsystem.theme.stashColors

@Composable
fun StashScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = MaterialTheme.stashColors.bgColor)
            .fillMaxSize()
    ) {
        Text(text = "Stash")
    }
}