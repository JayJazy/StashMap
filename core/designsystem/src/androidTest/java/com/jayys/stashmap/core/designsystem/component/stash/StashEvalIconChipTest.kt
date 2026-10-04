package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.testing.labelOf
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import com.jayys.stashmap.core.designsystem.theme.stash.StashEvalState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashEvalIconChip] 계약 테스트
 *
 * 핵심은 contentDescription 기본값과 "명시한 null".
 * 예전에 `contentDescription ?: style.label` 엘비스가 있어서 호출부가 null 을 줘도 라벨이 되살아났고,
 * 옆에 같은 라벨을 그리는 자리(StatGrid 등)에서 중복 announce 가 났다
 */
class StashEvalIconChipTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `contentDescription을_생략하면_상태_라벨이_접근성_설명으로_붙는다`() {
        composeTestRule.setStashContent {
            Column {
                StashEvalState.entries.forEach { StashEvalIconChip(state = it) }
            }
        }

        StashEvalState.entries.forEach { state ->
            composeTestRule.onNodeWithContentDescription(labelOf(state)).assertIsDisplayed()
        }
    }

    @Test
    fun `contentDescription에_null을_명시하면_접근성_설명이_사라진다`() {
        composeTestRule.setStashContent {
            Column {
                StashEvalState.entries.forEach {
                    StashEvalIconChip(
                        state = it,
                        contentDescription = null,
                        modifier = Modifier.testTag("chip_${it.name}"),
                    )
                }
            }
        }

        StashEvalState.entries.forEach { state ->
            // 칩이 실제로 그려진 걸 먼저 확인 — 아무것도 안 떠도 통과하는 테스트가 되면 의미가 없다
            composeTestRule.onNodeWithTag("chip_${state.name}").assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription(labelOf(state)).assertDoesNotExist()
        }
    }

    @Test
    fun `contentDescription을_직접_주면_그_문구가_그대로_쓰인다`() {
        composeTestRule.setStashContent {
            StashEvalIconChip(state = StashEvalState.Avoid, contentDescription = "평가 바꾸기")
        }

        composeTestRule.onNodeWithContentDescription("평가 바꾸기").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(labelOf(StashEvalState.Avoid))
            .assertDoesNotExist()
    }

    @Test
    fun `onClick을_주면_칩이_클릭_가능해지고_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashEvalIconChip(state = StashEvalState.Favorite, onClick = { clicks++ })
        }

        composeTestRule.onNodeWithContentDescription(labelOf(StashEvalState.Favorite))
            .assertHasClickAction()
            .performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `onClick이_없으면_표시_전용이라_클릭_액션이_없다`() {
        composeTestRule.setStashContent {
            StashEvalIconChip(state = StashEvalState.Favorite)
        }

        composeTestRule.onNodeWithContentDescription(labelOf(StashEvalState.Favorite))
            .assertHasNoClickAction()
    }
}
