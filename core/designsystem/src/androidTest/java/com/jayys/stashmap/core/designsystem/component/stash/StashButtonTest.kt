package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashButton] 계약 테스트
 *
 * 로딩 중 중복 탭은 "저장 두 번" 같은 실제 사고로 이어지는 자리.
 * enabled 와 loading 둘 다 클릭을 막아야 한다
 */
class StashButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val tag = "stashButton"

    @Test
    fun `활성_상태에서는_누르면_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashButton(text = "저장", onClick = { clicks++ })
        }

        composeTestRule.onNodeWithText("저장").assertIsEnabled().performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `enabled가_false면_눌러도_콜백이_오지_않는다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashButton(
                text = "저장",
                onClick = { clicks++ },
                enabled = false,
                modifier = Modifier.testTag(tag),
            )
        }

        composeTestRule.onNodeWithTag(tag).assertIsNotEnabled().performClick()

        assertEquals(0, clicks)
    }

    @Test
    fun `loading이면_눌러도_콜백이_오지_않는다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashButton(
                text = "저장",
                onClick = { clicks++ },
                loading = true,
                modifier = Modifier.testTag(tag),
            )
        }

        composeTestRule.onNodeWithTag(tag).assertIsNotEnabled().performClick()

        assertEquals(0, clicks)
    }

    @Test
    fun `loading이면_라벨_대신_인디케이터가_들어간다`() {
        composeTestRule.setStashContent {
            StashButton(
                text = "저장",
                onClick = {},
                loading = true,
                modifier = Modifier.testTag(tag),
            )
        }

        composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
        composeTestRule.onNodeWithText("저장").assertDoesNotExist()
    }

    @Test
    fun `variant를_바꿔도_라벨과_클릭_계약은_그대로다`() {
        val clicked = mutableListOf<StashButtonVariant>()

        composeTestRule.setStashContent {
            StashButton(
                text = "삭제",
                onClick = { clicked += StashButtonVariant.Destructive },
                variant = StashButtonVariant.Destructive,
                size = StashButtonSize.Lg,
            )
        }

        composeTestRule.onNodeWithText("삭제").performClick()

        assertEquals(listOf(StashButtonVariant.Destructive), clicked)
    }
}
