package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashTextField] 계약 테스트
 *
 * decorationBox 를 직접 짰기 때문에 placeholder 가 값과 겹쳐 남는 실수가 나기 쉽다
 */
class StashTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `값이_비어_있으면_placeholder가_보인다`() {
        composeTestRule.setStashContent {
            StashTextField(value = "", onValueChange = {}, placeholder = "가게 이름", label = "이름")
        }

        composeTestRule.onNodeWithText("가게 이름").assertIsDisplayed()
        composeTestRule.onNodeWithText("이름").assertIsDisplayed()
    }

    @Test
    fun `값이_있으면_placeholder_대신_값이_보인다`() {
        composeTestRule.setStashContent {
            StashTextField(value = "성수동 국밥", onValueChange = {}, placeholder = "가게 이름")
        }

        composeTestRule.onNodeWithText("성수동 국밥").assertIsDisplayed()
        composeTestRule.onNodeWithText("가게 이름").assertDoesNotExist()
    }

    @Test
    fun `입력하면_onValueChange로_전달되고_placeholder가_사라진다`() {
        var captured = ""

        composeTestRule.setStashContent {
            var text by remember { mutableStateOf("") }
            StashTextField(
                value = text,
                onValueChange = { text = it; captured = it },
                placeholder = "가게 이름",
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("국밥")

        assertEquals("국밥", captured)
        composeTestRule.onNodeWithText("가게 이름").assertDoesNotExist()
    }

    @Test
    fun `supportingText는_에러_여부와_무관하게_보인다`() {
        composeTestRule.setStashContent {
            StashTextField(
                value = "",
                onValueChange = {},
                isError = true,
                supportingText = "이름을 입력해주세요",
            )
        }

        composeTestRule.onNodeWithText("이름을 입력해주세요").assertIsDisplayed()
    }
}
