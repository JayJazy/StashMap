package com.jayys.stashmap.core.designsystem.component.legacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayys.stashmap.core.designsystem.R
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.layout.SpacerWidth
import com.jayys.stashmap.core.designsystem.modifier.clickableNoRipple
import com.jayys.stashmap.core.designsystem.theme.stash.StashIconSize
import com.jayys.stashmap.core.designsystem.theme.stash.StashMinTouchTarget
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography

@Composable
fun SMTopBar(
    topBarTitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.stashColorTokens.bg)
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = StashSpacing.s3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            // 아이콘은 24dp 라 터치 타깃만 48dp 로 넓힌다
            modifier = Modifier
                .sizeIn(minWidth = StashMinTouchTarget, minHeight = StashMinTouchTarget)
                .clickableNoRipple(
                    role = Role.Button,
                    onClickLabel = stringResource(R.string.back),
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = StashIcons.ChevronLeft,
                contentDescription = null,
                tint = MaterialTheme.stashColorTokens.fg,
                modifier = Modifier.size(StashIconSize.lg),
            )
        }

        SpacerWidth(4.dp)

        Text(
            text = topBarTitle,
            color = MaterialTheme.stashColorTokens.fg,
            style = MaterialTheme.stashTypography.h3
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSMTopBar() {
    SMTopBar(
        topBarTitle = "profile",
        onClick = {}
    )
}