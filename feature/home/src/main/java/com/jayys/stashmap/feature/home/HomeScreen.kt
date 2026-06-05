package com.jayys.stashmap.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = MaterialTheme.stashColorTokens.bg)
            .fillMaxSize()
    ) {
        Text(text = "Home", color = MaterialTheme.stashColorTokens.fg)
    }
}