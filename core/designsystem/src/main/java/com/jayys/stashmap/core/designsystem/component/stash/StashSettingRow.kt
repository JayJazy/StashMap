package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.modifier.toggleableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 설정 목록의 한 줄 — 아이콘 타일 + 라벨 + 현재 값
 *
 * [trailing] 을 주면 값·화살표 대신 그 슬롯이 들어감.
 * 스위치가 필요하면 [StashSwitchRow] 를 쓸 것 — 여기에 Switch 를 직접 넣으면 시맨틱이 어긋난다
 *
 * @param label 설정 이름
 * @param icon 라벨 앞 아이콘 (선택)
 * @param value 우측에 보일 현재 값
 * @param onClick 행 클릭 콜백 (null 이면 비클릭 + 화살표 숨김)
 * @param trailing 우측 커스텀 슬롯
 */
@Composable
fun StashSettingRow(
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    value: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val colors = MaterialTheme.stashColorTokens

    val base = modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 56.dp)
    val withClick = if (onClick != null) {
        base.clickableNoRipple(role = Role.Button, onClick = onClick)
    } else {
        base
    }

    Row(
        modifier = withClick.padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s2),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Box(
                modifier = Modifier
                    .size(SettingIconTileSize)
                    .clip(StashRadius.md)
                    .background(colors.surface2),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colors.fgMuted,
                    modifier = Modifier.size(StashIconSize.md),
                )
            }
        }

        StashText(
            text = label,
            role = StashTextRole.Body,
            color = colors.fg,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        if (trailing != null) {
            trailing()
        } else {
            if (value != null) {
                StashText(
                    text = value,
                    role = StashTextRole.BodySm,
                    color = colors.fgMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (onClick != null) {
                Icon(
                    imageVector = StashIcons.ChevronRight,
                    contentDescription = null,
                    tint = colors.fgSubtle,
                    modifier = Modifier.size(StashIconSize.md),
                )
            }
        }
    }
}

/**
 * 스위치가 달린 설정 행
 *
 * 행 전체가 하나의 토글이라 스위치만 따로 누를 필요가 없고, TalkBack 도 "라벨, 스위치, 켜짐" 으로 한 번에 읽음
 *
 * @param label 설정 이름
 * @param checked 켜짐 여부
 * @param onCheckedChange 토글 콜백
 * @param icon 라벨 앞 아이콘 (선택)
 */
@Composable
fun StashSwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    StashSettingRow(
        label = label,
        modifier = modifier.toggleableNoRipple(
            value = checked,
            role = Role.Switch,
            onValueChange = onCheckedChange,
        ),
        icon = icon,
        // 토글 시맨틱은 행이 갖는다 — 스위치는 그림만
        trailing = { Switch(checked = checked, onCheckedChange = null) },
    )
}

private val SettingIconTileSize = 36.dp

@Composable
private fun StashSettingRowShowcase() {
    var dark by remember { mutableStateOf(false) }
    val colors = MaterialTheme.stashColorTokens

    Column(modifier = Modifier.background(colors.bg).padding(StashSpacing.s4)) {
        StashCard(contentPadding = PaddingValues(vertical = StashSpacing.s2)) {
            StashSettingRow(
                label = "언어",
                icon = StashIcons.Languages,
                value = "한국어",
                onClick = {},
            )
            StashDivider()
            StashSwitchRow(
                label = "다크 모드",
                checked = dark,
                onCheckedChange = { dark = it },
                icon = StashIcons.Moon,
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashSettingRowLightPreview() {
    StashTheme(darkTheme = false) {
        StashSettingRowShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashSettingRowDarkPreview() {
    StashTheme(darkTheme = true) {
        StashSettingRowShowcase()
    }
}
