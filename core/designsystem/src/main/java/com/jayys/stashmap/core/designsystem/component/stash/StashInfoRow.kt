package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 상세 화면의 읽기 전용 정보 행 — 주소, 방문일, 가격대 등
 *
 * 라벨 폭을 고정해 여러 행을 쌓았을 때 값의 시작점이 맞음
 *
 * @param label 항목 이름
 * @param value 항목 값
 * @param icon 라벨 앞 아이콘 (선택)
 */
@Composable
fun StashInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    val colors = MaterialTheme.stashColorTokens

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .padding(vertical = StashSpacing.s2),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.fgMuted,
                modifier = Modifier.size(StashIconSize.md),
            )
        }

        StashText(
            text = label,
            role = StashTextRole.BodySm,
            color = colors.fgMuted,
            modifier = Modifier.widthIn(min = LabelWidth),
        )

        StashText(
            text = value,
            role = StashTextRole.Body,
            color = colors.fg,
            modifier = Modifier.weight(1f),
        )
    }
}

/** 라벨 열 최소 폭 — 값의 시작점을 맞추되, 긴 로케일에서는 라벨이 더 넓어지도록 min 으로 */
private val LabelWidth = 72.dp

@Composable
private fun StashInfoRowShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        StashInfoRow(label = "주소", value = "서울 성동구 연무장길 12", icon = StashIcons.MapPin)
        StashInfoRow(label = "방문일", value = "2026.05.21", icon = StashIcons.Calendar)
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashInfoRowLightPreview() {
    StashTheme(darkTheme = false) {
        StashInfoRowShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashInfoRowDarkPreview() {
    StashTheme(darkTheme = true) {
        StashInfoRowShowcase()
    }
}
