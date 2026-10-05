package com.jayys.stashmap.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashMinTouchTarget
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.feature.home.R

/** 섹션 제목 + 우측 "전체보기" */
@Composable
fun HomeSectionLabel(
    title: String,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StashText(
            text = title,
            modifier = Modifier.weight(1f),
            role = StashTextRole.H3,
        )

        // 글자만으로는 터치 타깃이 모자라 바깥 박스를 48dp 로 넓힌다
        Box(
            modifier = Modifier
                .sizeIn(minHeight = StashMinTouchTarget)
                .clickableNoRipple(role = Role.Button, onClick = onSeeAllClick)
                .padding(start = StashSpacing.s3),
            contentAlignment = Alignment.Center,
        ) {
            StashText(
                text = stringResource(R.string.home_see_all),
                role = StashTextRole.LabelStrong,
                color = colors.fgMuted,
            )
        }
    }
}

@Composable
private fun HomeSectionLabelPreviewContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        HomeSectionLabel(
            title = stringResource(R.string.home_section_recent),
            onSeeAllClick = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeSectionLabelLightPreview() {
    StashTheme(darkTheme = false) {
        HomeSectionLabelPreviewContent()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeSectionLabelDarkPreview() {
    StashTheme(darkTheme = true) {
        HomeSectionLabelPreviewContent()
    }
}
