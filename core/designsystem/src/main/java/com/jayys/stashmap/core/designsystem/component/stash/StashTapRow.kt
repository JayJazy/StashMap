package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashRadius
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens

/**
 * 탭하면 피커·다음 화면으로 넘어가는 입력 행 — 날짜 선택, 장소 검색 진입 등
 *
 * 입력 필드와 같은 테두리·높이라 [StashTextField] 와 한 폼에 섞어 써도 줄이 맞음
 *
 * @param text 현재 값 (null·공백이면 [placeholder] 표시)
 * @param onClick 행 클릭 콜백
 * @param placeholder 값이 없을 때 안내 문구
 * @param leadingIcon 값 앞 아이콘 (선택)
 */
@Composable
fun StashTapRow(
    text: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
) {
    val colors = MaterialTheme.stashColorTokens
    val hasValue = !text.isNullOrBlank()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 44.dp)
            .clip(StashRadius.md)
            .background(colors.fieldBg)
            .border(1.dp, colors.fieldBorder, StashRadius.md)
            .clickableNoRipple(role = Role.Button, onClick = onClick)
            .padding(horizontal = StashSpacing.s3, vertical = StashSpacing.s2),
        horizontalArrangement = Arrangement.spacedBy(StashSpacing.s2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = colors.fgMuted,
                modifier = Modifier.size(StashIconSize.md),
            )
        }

        StashText(
            text = if (hasValue) text.orEmpty() else placeholder.orEmpty(),
            role = StashTextRole.Body,
            color = if (hasValue) colors.fg else colors.fgSubtle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Icon(
            imageVector = StashIcons.ChevronRight,
            contentDescription = null,
            tint = colors.fgSubtle,
            modifier = Modifier.size(StashIconSize.md),
        )
    }
}

@Composable
private fun StashTapRowShowcase() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
        verticalArrangement = Arrangement.spacedBy(StashSpacing.s3),
    ) {
        StashTapRow(text = "2026.05.21", onClick = {}, leadingIcon = StashIcons.Calendar)
        StashTapRow(text = null, onClick = {}, placeholder = "방문일 선택", leadingIcon = StashIcons.Calendar)
        StashTapRow(text = "서울 성동구 연무장길 12", onClick = {}, leadingIcon = StashIcons.MapPin)
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashTapRowLightPreview() {
    StashTheme(darkTheme = false) {
        StashTapRowShowcase()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun StashTapRowDarkPreview() {
    StashTheme(darkTheme = true) {
        StashTapRowShowcase()
    }
}
