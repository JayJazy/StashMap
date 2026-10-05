package com.jayys.stashmap.feature.home.ui

import androidx.annotation.PluralsRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.jayys.stashmap.core.designsystem.component.stash.StashText
import com.jayys.stashmap.core.designsystem.layout.SpacerHeight
import com.jayys.stashmap.core.designsystem.theme.stash.StashSpacing
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import com.jayys.stashmap.core.designsystem.theme.stash.StashTheme
import com.jayys.stashmap.core.designsystem.theme.stash.stashColorTokens
import com.jayys.stashmap.core.designsystem.theme.stash.stashTypography
import com.jayys.stashmap.feature.home.R
import com.jayys.stashmap.feature.home.model.HomeMonthlySummary

/** 인사말 + 이번 달 요약 두 줄 — 숫자만 색으로 강조 */
@Composable
fun HomeGreeting(
    summary: HomeMonthlySummary,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.stashColorTokens
    val headlineStyle = MaterialTheme.stashTypography.h1

    Column(modifier = modifier.fillMaxWidth()) {
        StashText(text = stringResource(R.string.home_greeting), role = StashTextRole.BodySm)

        SpacerHeight(StashSpacing.s2)

        Text(
            text = countHighlighted(R.plurals.home_monthly_recorded, summary.recordedCount, colors.accent),
            color = colors.fg,
            style = headlineStyle,
        )
        Text(
            text = countHighlighted(R.plurals.home_monthly_revisit, summary.revisitCount, colors.success),
            color = colors.fg,
            style = headlineStyle,
        )
    }
}

/**
 * 숫자 자리만 색을 입힌 문장
 *
 * 완성된 문장에서 숫자를 indexOf 로 찾으면, 번역문 앞쪽에 다른 숫자가 있을 때 엉뚱한 글자를 강조한다.
 * 그래서 [CountSlot] 을 먼저 끼워 포맷해 **슬롯 위치**를 잡고 그 자리에 숫자를 넣는다
 */
@Composable
private fun countHighlighted(@PluralsRes id: Int, count: Int, color: Color): AnnotatedString {
    val template = pluralStringResource(id, count, CountSlot)
    val slot = template.indexOf(CountSlot)
    val value = count.toString()

    return buildAnnotatedString {
        if (slot < 0) {
            // 번역에서 placeholder 가 빠진 경우 — 강조만 포기하고 문장은 그대로
            append(template)
            return@buildAnnotatedString
        }
        append(template.substring(0, slot))
        withStyle(SpanStyle(color = color)) { append(value) }
        append(template.substring(slot + CountSlot.length))
    }
}

/** 포맷 슬롯 표시용 — 번역문에 나올 리 없는 private use area 문자 */
private const val CountSlot = "\uE000"

@Composable
private fun HomeGreetingPreviewContent() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.stashColorTokens.bg)
            .padding(StashSpacing.s4),
    ) {
        HomeGreeting(summary = HomeMonthlySummary(recordedCount = 8, revisitCount = 3))
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeGreetingLightPreview() {
    StashTheme(darkTheme = false) {
        HomeGreetingPreviewContent()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun HomeGreetingDarkPreview() {
    StashTheme(darkTheme = true) {
        HomeGreetingPreviewContent()
    }
}
