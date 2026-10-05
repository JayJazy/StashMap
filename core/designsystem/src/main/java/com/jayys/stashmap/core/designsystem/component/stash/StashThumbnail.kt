package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 가게 사진 썸네일 — 사진이 없으면 회색 플레이스홀더
 *
 * @param painter 썸네일 이미지 (null 이면 플레이스홀더)
 * @param size 한 변 길이
 * @param contentDescription 이미지 접근성 설명 (장식용이면 null)
 */
@Composable
fun StashThumbnail(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    size: Dp = 64.dp,
    contentDescription: String? = null,
) {
    val colors = MaterialTheme.stashColorTokens

    Box(
        modifier = modifier
            .size(size)
            .clip(StashRadius.md)
            .background(colors.surface3),
        contentAlignment = Alignment.Center,
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Icon(
                imageVector = StashIcons.Photo,
                contentDescription = null,
                tint = colors.fgSubtle,
                modifier = Modifier.size(size / 3),
            )
        }
    }
}

@Composable
private fun StashThumbnailShowcase() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashThumbnail(size = 48.dp)
        StashThumbnail(size = 64.dp)
        StashThumbnail(size = 96.dp)
    }
}

@Preview(showBackground = true)
@Composable
private fun StashThumbnailLightPreview() {
    StashTheme(darkTheme = false) {
        StashThumbnailShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StashThumbnailDarkPreview() {
    StashTheme(darkTheme = true) {
        StashThumbnailShowcase()
    }
}
