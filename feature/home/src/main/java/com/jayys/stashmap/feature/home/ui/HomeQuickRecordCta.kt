package com.jayys.stashmap.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashShadowLevel
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashShadow
import com.jayys.stashmap.feature.home.R

/** 등록 화면으로 바로 들어가는 accent 배너 CTA */
@Composable
fun HomeQuickRecordCta(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens

    Row(
        modifier = modifier
            .fillMaxWidth()
            .stashShadow(level = StashShadowLevel.Sm, shape = StashRadius.lg)
            .clip(StashRadius.lg)
            .background(colors.accent)
            .clickableNoRipple(role = Role.Button, onClick = onClick)
            .padding(StashSpacing.s4),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(IconTileSize)
                .clip(StashRadius.md)
                // accent 위에 올리는 반투명 타일 — 라이트는 흰빛, 다크는 어두운 빛으로 자동으로 뒤집힌다
                .background(colors.accentFg.copy(alpha = IconTileAlpha)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = StashIcons.Plus,
                contentDescription = null,
                tint = colors.accentFg,
                modifier = Modifier.size(StashIconSize.lg),
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            StashText(
                text = stringResource(R.string.home_quick_record_title),
                role = StashTextRole.LabelStrong,
                color = colors.accentFg,
            )
            StashText(
                text = stringResource(R.string.home_quick_record_subtitle),
                role = StashTextRole.Caption,
                // 알파를 곱하면 다크에서 대비가 3.88:1 로 떨어져 AA 미달 — accentFg 원색을 쓴다
                color = colors.accentFg,
            )
        }

        Icon(
            imageVector = StashIcons.ChevronRight,
            contentDescription = null,
            tint = colors.accentFg,
            modifier = Modifier.size(StashIconSize.md),
        )
    }
}

private val IconTileSize = 44.dp
private const val IconTileAlpha = 0.2f

@Composable
private fun HomeQuickRecordCtaPreviewContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        HomeQuickRecordCta(onClick = {})
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeQuickRecordCtaLightPreview() {
    StashTheme(darkTheme = false) {
        HomeQuickRecordCtaPreviewContent()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeQuickRecordCtaDarkPreview() {
    StashTheme(darkTheme = true) {
        HomeQuickRecordCtaPreviewContent()
    }
}
