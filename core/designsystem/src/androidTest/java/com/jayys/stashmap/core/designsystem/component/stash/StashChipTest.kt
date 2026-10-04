package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashChip] 계약 테스트
 *
 * 필터 칩은 선택 여부가 배경색으로만 바뀌기 쉬운데, 그러면 TalkBack 에서 구분이 안 된다
 */
class StashChipTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `선택된_칩만_선택_시맨틱을_갖는다`() {
        composeTestRule.setStashContent {
            Row {
                StashChip(label = "한식", selected = true, onClick = {})
                StashChip(label = "일식", selected = false, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("한식").assertIsSelected()
        composeTestRule.onNodeWithText("일식").assertIsNotSelected()
    }

    @Test
    fun `칩을_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashChip(label = "한식", onClick = { clicks++ }, leadingIcon = StashIcons.Utensils)
        }

        composeTestRule.onNodeWithText("한식").performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `onClick이_없으면_라벨만_그리는_비클릭_칩이다`() {
        composeTestRule.setStashContent {
            StashChip(label = "한식")
        }

        composeTestRule.onNodeWithText("한식").assertIsDisplayed().assertHasNoClickAction()
    }
}
