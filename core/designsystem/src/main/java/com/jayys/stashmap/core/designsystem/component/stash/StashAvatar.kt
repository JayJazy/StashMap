package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * Stash Design System 아바타.
 *
 * 원형 클립. [painter] 가 있으면 이미지를, 없으면 accentSubtle 배경 + accentSubtleFg 이니셜을 표시한다.
 *
 * @param painter 표시할 이미지 (선택)
 * @param initials 이미지가 없을 때 표시할 이니셜
 * @param size 아바타 지름
 * @param contentDescription 이미지 접근성 설명 (장식용이면 null)
 */
@Composable
fun StashAvatar(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    initials: String? = null,
    size: Dp = 40.dp,
    contentDescription: String? = null,
) {
    val colors = MaterialTheme.stashColorTokens

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(if (painter == null) colors.accentSubtle else colors.surface2),
        contentAlignment = Alignment.Center,
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(size).clip(CircleShape),
            )
        } else if (!initials.isNullOrBlank()) {
            StashText(
                text = initials,
                role = StashTextRole.Label,
                color = colors.accentSubtleFg,
            )
        }
    }
}

@Composable
private fun StashAvatarShowcase() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashAvatar(initials = "JY", size = 40.dp)
        StashAvatar(initials = "AB", size = 56.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun StashAvatarLightPreview() {
    StashTheme(darkTheme = false) {
        StashAvatarShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashAvatarDarkPreview() {
    StashTheme(darkTheme = true) {
        StashAvatarShowcase()
    }
}
