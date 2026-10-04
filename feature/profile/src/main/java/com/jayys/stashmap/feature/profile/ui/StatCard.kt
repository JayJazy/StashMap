package com.jayys.stashmap.feature.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.layout.SpacerHeight
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.isStashDarkTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography

@Composable
fun StatCard(
    title: String,
    content: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    height: Dp = 78.dp
) {
    val isDarkMode = MaterialTheme.isStashDarkTheme

    Card(
        modifier = modifier
            .height(height),
        shape = StashRadius.md,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.stashColorTokens.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = if (isDarkMode) {
            BorderStroke(1.dp, MaterialTheme.stashColorTokens.border.copy(alpha = 0.2f))
        } else {
            null
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )

                Text(
                    text = title,
                    color = MaterialTheme.stashColorTokens.fg,
                    style = MaterialTheme.stashTypography.body
                )
            }

            SpacerHeight(12.dp)

            Text(
                text = content,
                color = MaterialTheme.stashColorTokens.fg,
                style = MaterialTheme.stashTypography.h3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStatCard() {
    StatCard(
        title = "Favorites",
        content = "9999",
        icon = StashIcons.Smile,
        iconTint = MaterialTheme.stashColorTokens.success
    )
}