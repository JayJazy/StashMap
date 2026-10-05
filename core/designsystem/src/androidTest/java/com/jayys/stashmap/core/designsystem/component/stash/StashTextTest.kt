package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import com.jayys.stashmap.core.designsystem.theme.stash.StashTextRole
import org.junit.Rule
import org.junit.Test

/**
 * [StashText] 계약 테스트
 *
 * Overline 만 유일하게 텍스트를 변형한다 — 호출부가 이미 대문자로 넘겼다고 착각하기 쉬운 자리
 */
class StashTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `Overline_역할은_대문자로_변환해_그린다`() {
        composeTestRule.setStashContent {
            StashText(text = "overline", role = StashTextRole.Overline)
        }

        composeTestRule.onNodeWithText("OVERLINE").assertIsDisplayed()
        composeTestRule.onNodeWithText("overline").assertDoesNotExist()
    }

    @Test
    fun `다른_역할은_받은_문자열을_그대로_그린다`() {
        composeTestRule.setStashContent {
            StashText(text = "body text", role = StashTextRole.Body)
        }

        composeTestRule.onNodeWithText("body text").assertIsDisplayed()
    }
}
