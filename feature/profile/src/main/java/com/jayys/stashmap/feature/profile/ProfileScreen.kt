package com.jayys.stashmap.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.R

@Composable
fun ProfileScreen(
    isDarkMode: MutableState<Boolean>,
    onDarkModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = colorResource(id = R.color.bg_color))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Switch(
                checked = isDarkMode.value,
                onCheckedChange = onDarkModeChange,
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "Profile",
                color = colorResource(id = R.color.green10)
            )
        }
    }
}