package com.jayys.stashmap.feature.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography
import com.jayys.stashmap.feature.main.nav.STASH_MAIN_NAV_ITEMS

@Composable
fun MainBottomBar(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.stashColorTokens.surface)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        STASH_MAIN_NAV_ITEMS.forEach { item ->
            val selected = backStack.lastOrNull() == item.route

            Column(
                modifier = Modifier
                    .clickableNoRipple(role = Role.Tab) {
                        if (backStack.lastOrNull() != item.route) {
                            backStack.clear()
                            backStack.add(item.route)
                        }
                    }
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ico_home),
                    contentDescription = item.label,
                    tint = if (selected) {
                        MaterialTheme.stashColorTokens.accent
                    } else {
                        MaterialTheme.stashColorTokens.fgMuted
                    }
                )

                Text(
                    text = item.label,
                    style = MaterialTheme.stashTypography.caption,
                    color = if (selected) {
                        MaterialTheme.stashColorTokens.accent
                    } else {
                        MaterialTheme.stashColorTokens.fgMuted
                    }
                )
            }
        }
    }
}