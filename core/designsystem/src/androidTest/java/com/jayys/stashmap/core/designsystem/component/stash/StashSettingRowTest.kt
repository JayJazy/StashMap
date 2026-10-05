package com.jayys.stashmap.core.designsystem.component.stash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jayys.stashmap.core.designsystem.icon.StashIcons
import com.jayys.stashmap.core.designsystem.testing.setStashContent
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * [StashSettingRow] / [StashSwitchRow] 계약 테스트
 *
 * 스위치 행은 "행 전체가 토글, Switch 는 그림" 이 핵심.
 * Switch 에 onCheckedChange 를 다시 달면 토글 노드가 둘이 되고 TalkBack 이 라벨과 상태를 따로 읽는다
 */
class StashSettingRowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `라벨과_현재_값이_함께_보인다`() {
        composeTestRule.setStashContent {
            StashSettingRow(label = "언어", icon = StashIcons.Languages, value = "한국어", onClick = {})
        }

        composeTestRule.onNodeWithText("언어").assertIsDisplayed()
        composeTestRule.onNodeWithText("한국어").assertIsDisplayed()
    }

    @Test
    fun `onClick을_주면_행_전체가_눌리고_콜백이_온다`() {
        var clicks = 0

        composeTestRule.setStashContent {
            StashSettingRow(label = "언어", value = "한국어", onClick = { clicks++ })
        }

        composeTestRule.onNodeWithText("언어").assertHasClickAction().performClick()

        assertEquals(1, clicks)
    }

    @Test
    fun `onClick이_없으면_행이_클릭되지_않는다`() {
        composeTestRule.setStashContent {
            StashSettingRow(label = "버전", value = "1.0.0")
        }

        composeTestRule.onNodeWithText("버전").assertHasNoClickAction()
    }

    @Test
    fun `trailing_슬롯을_주면_값_대신_그_슬롯이_들어간다`() {
        composeTestRule.setStashContent {
            StashSettingRow(
                label = "알림",
                value = "켜짐",
                trailing = { StashText(text = "커스텀") },
            )
        }

        composeTestRule.onNodeWithText("커스텀").assertIsDisplayed()
        composeTestRule.onNodeWithText("켜짐").assertDoesNotExist()
    }

    @Test
    fun `스위치_행은_라벨과_상태가_한_토글_노드로_합쳐져_읽힌다`() {
        composeTestRule.setStashContent {
            StashSwitchRow(label = "다크 모드", checked = true, onCheckedChange = {})
        }

        // 스위치는 onCheckedChange = null 이라 토글 노드는 행 하나뿐
        composeTestRule.onAllNodes(isToggleable()).assertCountEquals(1)
        composeTestRule.onNode(hasText("다크 모드") and isToggleable())
            .assertIsOn()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Switch))
    }

    @Test
    fun `스위치_행은_행_아무_데나_눌러도_토글된다`() {
        val changes = mutableListOf<Boolean>()

        composeTestRule.setStashContent {
            var checked by remember { mutableStateOf(false) }
            StashSwitchRow(
                label = "다크 모드",
                checked = checked,
                onCheckedChange = { checked = it; changes += it },
                icon = StashIcons.Moon,
            )
        }

        composeTestRule.onNodeWithText("다크 모드").assertIsOff().performClick()
        composeTestRule.onNodeWithText("다크 모드").assertIsOn().performClick()

        assertEquals(listOf(true, false), changes)
        composeTestRule.onNodeWithText("다크 모드").assertIsOff()
    }
}
