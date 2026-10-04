package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashTapRow] 계약 테스트
 *
 * "값 없음" 판정이 null 체크만이면 공백 문자열이 값처럼 보여 빈 줄이 뜬다 — isNullOrBlank 계약을 못 박는다
 */
class StashTapRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `값이_있으면_값이_보이고_placeholder는_숨는다`() {
        composeTestRule.setStashContent {
            StashTapRow(
                text = "2026.05.21",
                onClick = {},
                placeholder = "방문일 선택",
                leadingIcon = StashIcons.Calendar,
            )
        }

        composeTestRule.onNodeWithText("2026.05.21").assertIsDisplayed()
        composeTestRule.onNodeWithText("방문일 선택").assertDoesNotExist()
    }

    @Test
    fun `값이_null이면_placeholder가_보인다`() {
        composeTestRule.setStashContent {
            StashTapRow(text = null, onClick = {}, placeholder = "방문일 선택")
        }

        composeTestRule.onNodeWithText("방문일 선택").assertIsDisplayed()
    }

    @Test
    fun `값이_공백뿐이면_placeholder가_보인다`() {
        composeTestRule.setStashContent {
            StashTapRow(text = "   ", onClick = {}, placeholder = "방문일 선택")
        }

        composeTestRule.onNodeWithText("방문일 선택").assertIsDisplayed()
    }

    @Test
    fun `행을_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashTapRow(text = null, onClick = { clicks++ }, placeholder = "방문일 선택")
        }

        composeTestRule.onNodeWithText("방문일 선택").assertHasClickAction().performClick()

        assertEquals(1, clicks)
    }
}
