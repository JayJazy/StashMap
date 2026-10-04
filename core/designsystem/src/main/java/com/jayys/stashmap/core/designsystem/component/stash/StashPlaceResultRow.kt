package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.selectableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 장소 검색 결과 한 줄 — 카카오 키워드 검색 결과를 고르는 자리
 *
 * 고르면 행 전체가 accent 로 강조되고 우측에 체크가 붙음
 *
 * @param name 장소명
 * @param address 도로명 주소
 * @param onClick 행 클릭 콜백
 * @param category 카테고리 (예: "카페·디저트")
 * @param distance 현재 위치로부터의 거리 문자열 (예: "120m")
 * @param selected 선택 여부
 */
@Composable
fun StashPlaceResultRow(
    name: String,
    address: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    category: String? = null,
    distance: String? = null,
    selected: Boolean = false,
) {
    val colors = MaterialTheme.stashColorTokens

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .background(if (selected) colors.accentSubtle else colors.surface)
            .selectableNoRipple(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = StashSpacing.s4, vertical = StashSpacing.s3),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(PinTileSize)
                .clip(StashRadius.md)
                .background(if (selected) colors.accent else colors.surface2),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = StashIcons.MapPin,
                contentDescription = null,
                tint = if (selected) colors.accentFg else colors.fgMuted,
                modifier = Modifier.size(StashIconSize.md),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(StashSpacing.s1),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                StashText(
                    text = name,
                    role = StashTextRole.LabelStrong,
                    color = colors.fg,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )
                if (category != null) {
                    StashText(
                        text = category,
                        role = StashTextRole.Caption,
                        color = colors.fgMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            StashText(
                text = address,
                role = StashTextRole.BodySm,
                color = colors.fgMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (distance != null) {
            StashText(
                text = distance,
                role = StashTextRole.Caption,
                color = colors.fgMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (selected) {
            Icon(
                imageVector = StashIcons.Check,
                contentDescription = null,
                tint = colors.accent,
                modifier = Modifier.size(StashIconSize.md),
            )
        }
    }
}

private val PinTileSize = 40.dp

@Composable
private fun StashPlaceResultRowShowcase() {
    Column(modifier = Modifier.background(MaterialTheme.stashColorTokens.bg)) {
        StashPlaceResultRow(
            name = "스타벅스 강남R점",
            address = "서울 강남구 강남대로 390",
            onClick = {},
            category = "카페·디저트",
            distance = "120m",
            selected = true,
        )
        StashPlaceResultRow(
            name = "스타벅스 강남대로점",
            address = "서울 강남구 강남대로 358",
            onClick = {},
            category = "카페·디저트",
            distance = "210m",
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashPlaceResultRowLightPreview() {
    StashTheme(darkTheme = false) {
        StashPlaceResultRowShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashPlaceResultRowDarkPreview() {
    StashTheme(darkTheme = true) {
        StashPlaceResultRowShowcase()
    }
}
