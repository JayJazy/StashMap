package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashEmptyState] 계약 테스트
 *
 * 버튼은 actionLabel 과 onAction 이 둘 다 있을 때만 — 한쪽만 주면 "눌러도 아무 일 없는 버튼"
 * 또는 "라벨 없는 버튼"이 생긴다
 */
class StashEmptyStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `제목과_설명이_보인다`() {
        composeTestRule.setStashContent {
            StashEmptyState(title = "첫 맛집을 기록해보세요", description = "다녀온 가게를 남겨보세요.")
        }

        composeTestRule.onNodeWithText("첫 맛집을 기록해보세요").assertIsDisplayed()
        composeTestRule.onNodeWithText("다녀온 가게를 남겨보세요.").assertIsDisplayed()
    }

    @Test
    fun `actionLabel과_onAction이_둘_다_있으면_버튼이_보이고_눌린다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashEmptyState(
                title = "비어 있어요",
                actionLabel = "기록하기",
                onAction = { clicks++ },
            )
        }

        composeTestRule.onNodeWithText("기록하기").assertIsDisplayed().performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `onAction_없이_actionLabel만_주면_버튼이_보이지_않는다`() {
        composeTestRule.setStashContent {
            StashEmptyState(title = "비어 있어요", actionLabel = "기록하기")
        }

        composeTestRule.onNodeWithText("비어 있어요").assertIsDisplayed()
        composeTestRule.onNodeWithText("기록하기").assertDoesNotExist()
        composeTestRule.onAllNodes(hasClickAction()).assertCountEquals(0)
    }

    @Test
    fun `actionLabel_없이_onAction만_주면_버튼이_보이지_않는다`() {
        composeTestRule.setStashContent {
            StashEmptyState(title = "비어 있어요", onAction = {})
        }

        composeTestRule.onNodeWithText("비어 있어요").assertIsDisplayed()
        composeTestRule.onAllNodes(hasClickAction()).assertCountEquals(0)
    }
}
